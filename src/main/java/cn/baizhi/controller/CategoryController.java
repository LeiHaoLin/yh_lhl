package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService cs;
    private Logger log = LoggerFactory.getLogger(CategoryController.class);

    @RequestMapping("/queryByLevels")
    public List<Category> queryByLevels(int levels){
        return cs.queryByLevels(levels);
    }

    @RequestMapping("/queryByParendId")
    public List<Category> queryByParendId(String id){
        return cs.queryByParendId(id);
    }

    @RequestMapping("/save")
    public String save(@RequestBody Category category){
        cs.save(category);
        return null;
    }

    @RequestMapping("/delete")
    public String delete(String id){
        cs.delete(id);
        return null;
    }
}
