package com.yang.common.mybatis.schema;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.sql.Types;
import java.util.Collections;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/24
 */
public class CodeGenerator {



    public static void main(String[] args) {


        FastAutoGenerator.create("jdbc:mysql://localhost:3306/mybatis_study?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=UTC",
                        "root",
                        "123456")
                .globalConfig(builder -> {
                    builder.author("YangAns") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("D://code"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                                .moduleName("system") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("t_blog") // 设置需要生成的表名
                                .addTablePrefix("t_") // 设置过滤表前缀
                )
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }






}
