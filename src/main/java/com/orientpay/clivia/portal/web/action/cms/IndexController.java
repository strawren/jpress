package com.orientpay.clivia.portal.web.action.cms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.orientpay.clivia.component.constants.CliviaConstants;
import com.orientpay.clivia.component.dto.CmsPostDTO;
import com.orientpay.clivia.component.model.CmsPostMeta;
import com.orientpay.clivia.component.service.CmsPostService;
import com.orientpay.clivia.portal.util.RunYouUtils;
import com.orientpay.clivia.portal.web.auths.TvAuthsResponse;
import com.orientpay.oecs.framework.orm.query.Page;
import com.orientpay.oecs.framework.web.action.BaseMultiActionController;
import com.orientpay.oecs.util.misc.SystemPropertyUtils;

/**
 * 处理购物车,订购等请求
 *
 */
@Controller
@SuppressWarnings("unchecked")
public class IndexController extends BaseMultiActionController {

    @Autowired
    CmsPostService postService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * json转换类,线程安全,可全局重用
     *
     */
    private final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * 商品编号key
     */
    protected final String SYS_KEY_ITEM_NO = SystemPropertyUtils.getString("sys.key.itemno");

    /**
     * 购物车在session中的key
     */
    private final String SESSION_KEY_CART = "cart";
    
    /**
     * 获取机顶盒mac
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/getStbMac.do")
    public void getStbMac(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Get-STB_MAC", "1");
        String indexUrl = "index.html?" + CliviaConstants.GET_STB_MAC_PARAM + "=";

        // 重定向跳到润有商城首页
        response.sendRedirect(indexUrl);
    }
    
	/**
	 * 将某一件商品从购物车添加或移除
	 * @param session
	 * @param itemId
	 * @param flag false:删除,true:添加
	 * @param remove true:全部移除
	 * @return
	 */
    @RequestMapping("/addItemToCart.do")
	@ResponseBody
    public int addItemToCart(HttpSession session,Long itemId,boolean flag,boolean remove) {
        log.debug("addItemToCart begin...");

        log.debug("itemId:"+itemId);
        log.debug("operation flag:"+ (flag?"++":"--"));
        log.debug("delete from cart:"+remove);

        TreeMap<Long,Integer> cart = getCartFromSession(session);
        log.debug("original cart:"+cart);

        Integer count = cart.get(itemId);
        //如果购物车中没有该商品,数量初始化为0
        if (count==null) {
            count = 0;
        }

        if (flag) {
            //数量加1
            cart.put(itemId, ++count);
        }else {
            //数量减1
            cart.put(itemId, --count);
            //如果为0,从购物车中移除
            if (count<1) {
                cart.remove(itemId);
                count = 0;
            }
        }

        //remove参数为true,表示从购物车中删除该商品
        if (remove) {
            cart.remove(itemId);
            count = 0;
        }
        log.debug("shopping cart:"+cart);

        session.setAttribute(SESSION_KEY_CART, cart);
        log.debug("addItemToCart end!!!");
        return count;
    }

    /**
     * 从session中获取购物车
     * @param session
     * @return
     */
    private TreeMap<Long, Integer> getCartFromSession(HttpSession session) {
        TreeMap<Long,Integer> cart = (TreeMap<Long, Integer>) session.getAttribute(SESSION_KEY_CART);
        if (cart==null) {
            //购物车不存在则初始化,采用TreeMap方便分页排序
            cart = new TreeMap<Long,Integer>();
            session.setAttribute(SESSION_KEY_CART, cart);
        }
        return cart;
    }

    /**
	 * 家乐福页面,上一页,下一页跳转处理
	 * @param session
	 * @param pageNum
	 * @param selectedItems
	 * @return
	 */
	@RequestMapping("/carrefour.do")
    public String carrefourPage(HttpSession session,int pageNum) {
       log.debug("carrefourPage begin...");

       log.debug("parameter pageNum:"+pageNum);
       if (pageNum<1) {
           pageNum = 1;
       }

        log.debug("pageNum:"+pageNum);

        log.debug("carrefourPage end!!!");
        return "redirect:carrefour.html?pageNum="+pageNum;
    }

	/**
     * 家乐福购物车页面
     * @param session
     * @param selectedItems
     * @return
     */
    @RequestMapping("/toCart.do")
    public String toCart(HttpSession session,Page<CmsPostDTO> page,Model model) {
        log.debug("toCart begin...");
        log.debug("pageNum:"+page.getPageNum());
        log.debug("numPerPage:"+page.getNumPerPage());

        TreeMap<Long,Integer> cart = getCartFromSession(session);

        //获取当前页面的商品列表数据
        initPage(page,cart);

        log.debug("page totalCount:"+page.getTotalCount());
        log.debug("page totalPages:"+page.getTotalPages());
        log.debug("page result:"+page.getResult());

        model.addAttribute("page", page);

        log.debug("toCart end!!!");
        return "forward:shoppingCart.html";
    }

    /**
     * 订购单个商品
     * @param itemId
     * @param phone
     * @param request
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("orderItem.do")
    public String orderItem(Long itemId, String phone, HttpServletRequest request) throws IOException {
        log.debug("orderItem begin...");

        log.debug("itemId:"+itemId);
        log.debug("phone:"+phone);

        //单个商品,没有放入session,直接构建购物车
        TreeMap<Long,Integer> cart = new TreeMap<Long, Integer>();
        cart.put(itemId, 1);

        //从session中获取用户信息
        TvAuthsResponse userInfo = new TvAuthsResponse();
        userInfo.setInsAddress("博霞路160号4楼");
        userInfo.setUserName("亿付");
        
        //发送订购意向
        Map result = sendOrderIntention(cart, phone, userInfo);
        log.debug("order result:" + result.get("status"));

        //TODO 发送失败的处理?

        log.debug("orderItem end!!!");
        return "forward:orderSuccess.html";
    }

    /**
     * 发送订购意向
     * @param phone
     * @param cart
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private Map sendOrderIntention(TreeMap<Long, Integer> cart, String phone, TvAuthsResponse userInfo) throws IOException {
        //调用家乐福接口
        MultiValueMap<String, String> param = buildParam(cart, phone, userInfo);
        logger.info("order param:" + param);

        String result = restTemplate.postForObject(SystemPropertyUtils.getString(CliviaConstants.ORDER_INTENTION_REQ_URL), param, String.class);
        logger.debug("result:" + result);
        return jsonMapper.readValue(result, Map.class);
    }

    /**
     * 订购商品(针对购物车)
     * @param session
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/order.do")
    public String order(HttpSession session,String phone, TvAuthsResponse userInfo) throws IOException {
        log.debug("order begin...");

        //获取购物车
        TreeMap<Long,Integer> cart = getCartFromSession(session);

        Map result = sendOrderIntention(cart, phone, userInfo);
        log.debug("order result:"+result);

        //TODO 发送失败的处理
        //清空购物车
        session.removeAttribute(SESSION_KEY_CART);
        log.debug("order end!!!");
        return "forward:orderSuccess.html";
    }

    /**
     * 构建请求参数
     * @param cart
     * @param phone
     * @return
     * @throws IOException
     */
    private MultiValueMap<String, String> buildParam(TreeMap<Long, Integer> cart, String phone, TvAuthsResponse userInfo) throws IOException {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add("method", "createIntentOrder");
        
        Map<String, Object> signParamsMap = new HashMap<String, Object>();
        signParamsMap.put("appsecurity", SystemPropertyUtils.getString(CliviaConstants.ORDER_INTENTION_APP_SECURITY)); //润有商城提供
        signParamsMap.put("u_mobile", phone);
        signParamsMap.put("u_name", userInfo.getUserName());
        signParamsMap.put("u_address", userInfo.getInsAddress());
        String sign = RunYouUtils.getMd5Str(signParamsMap);

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("appid", SystemPropertyUtils.getString(CliviaConstants.ORDER_INTENTION_APPID));  //润有商城提供
        info.put("sign", sign);
        info.put("u_mobile", phone);
        info.put("u_name", userInfo.getUserName());
        info.put("u_address", userInfo.getInsAddress());
        info.put("remark", "备注");  //保留字段

        List<Map<String, Object>> goods = new ArrayList<Map<String, Object>>();

        for (Entry<Long, Integer> itemEntry : cart.entrySet()) {
            Map<String, Object> good = new HashMap<String, Object>();
            Long itemId = itemEntry.getKey();
            CmsPostDTO item = postService.getDetailById(itemId);

            String barcode = getItemNo(item);
            Integer count = itemEntry.getValue();
            good.put("barcode", barcode);
            //good.put("barcode", "00520009");  //润有提供的测试数据
            good.put("quantity", count);
            good.put("goodsname", item.getTitle());
            //good.put("goodsname", "测试商品1"); //润有提供的测试数据
            goods.add(good);
        }
        info.put("goods", goods);

        String json = jsonMapper.writeValueAsString(info);
        param.add("info", json);
        return param;
    }

    /**
     * 获取商品编号
     * @param item
     * @return
     */
    private String getItemNo(CmsPostDTO item) {
        CmsPostMeta cmsPostMeta = item.getMetaMap().get(SYS_KEY_ITEM_NO);

        if (cmsPostMeta!=null) {
            return cmsPostMeta.getValue();
        }

        return null;
    }

    /**
     * 模拟订购意向请求接口
     * @param method
     * @param info
     * @return
     */
    @RequestMapping("/orderApi.do")
    @ResponseBody
    public Map<String, String> orderApi(String method,String info) {
        log.debug("orderApi begin...");

        log.debug("method:"+method);
        log.debug("info:"+info);

        log.debug("orderApi end!!!");
        Map<String, String> result = new HashMap<String, String>();
        result.put("code", "0");
        result.put("order_no", "02014020410432102652");
        return result;
    }

    /**
     * 初始化分页相关内容
     * @param page
     * @param cart
     */
    private void initPage(Page<CmsPostDTO> page, Map<Long, Integer> cart) {
        //分页参数调整
        if (page.getPageNum()<1) {
            page.setPageNum(1);
        }
        page.setTotalCount(cart.size());
        page.setNumPerPage(7);

        long totalPages = page.getTotalPages();
        if (totalPages<page.getPageNum()) {
            page.setPageNum((int)totalPages);
        }

        //只查询当前页需要展示的商品详情
        int index = 0;
        List<CmsPostDTO> items = new ArrayList<CmsPostDTO>();
        for (Entry<Long, Integer> itemEntry : cart.entrySet()) {
            if (page.getFirst()<=index&&(page.getFirst()+page.getNumPerPage())>index) {
                CmsPostDTO item = postService.getDetailById(itemEntry.getKey());
                item.setCount(itemEntry.getValue());
                items.add(item);
            }

            index++;
        }

        page.setResult(items);
    }

}
