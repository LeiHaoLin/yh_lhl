package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao ad;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByLevels(int levels) {
        return ad.queryByLevels(levels);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByParendId(String id) {
        return ad.queryByParendId(id);
    }

    @DeleteCache
    @Override
    public void save(Category category) {
        category.setId(UUID.randomUUID().toString());
        ad.save(category);
    }

    @DeleteCache
    @Override
    public void delete(String id) {
        ad.delete(id);
    }
}
