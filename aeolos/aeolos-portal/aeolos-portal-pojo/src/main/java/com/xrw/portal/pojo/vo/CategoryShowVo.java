package com.xrw.portal.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/6/25 13:32
 */
@Data
public class CategoryShowVo {

    private Integer id;

    private Integer parentId;

    private String name;

    private String categoryImgUrl;

    private List<CategoryShowVo> categoryShowVoList=new ArrayList<>();
}
