package com.strawren.jpress.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.strawren.bsp.dao.BspMapper;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.jpress.dto.TermRelevanceTaxonomyDTO;


@BspMapper
public interface TermRelevanceTaxonomyMapper {
    @SuppressWarnings("rawtypes")
    public List searchTremRelevanceTaxonomy(BspCriterion oecscriterion, RowBounds rowbounds);
    public TermRelevanceTaxonomyDTO gettermTaxbyId(long termId);

    /**
     * 根据内容id获取关联目录的Id
     * @param objectId
     * @return
     */
    public List<Long> getTermIdByPostId(Long objectId);


    /**
     * 获取总条数
     * @param oecscriterion
     * @return
     */
    public abstract long count(BspCriterion oecscriterion);

    /**
     * 获取子菜单
     * @param param
     * @return
     */
}
