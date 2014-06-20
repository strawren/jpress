目录结构说明

参考 http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
src
  -main
      –bin 脚本库
      –java java源代码文件
      –resources 资源库，会自动复制到classes目录里
      –filters 资源过滤文件
      –assembly 组件的描述配置（如何打包）
      –config 配置文件
      –webapp web应用的目录。WEB-INF、css、js等
  -test
      –java 单元测试java源代码文件
      –resources 测试需要用的资源库
      –filters 测试资源过滤库
  -site Site（一些文档）
target
LICENSE.txt Project’s license
README.txt Project’s readme

工程根目录下就只有src和target两个目录

target是有存放项目构建后的文件和目录，jar包、war包、编译的class文件等。

target里的所有内容都是maven构建的时候生成的