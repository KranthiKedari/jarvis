package com.kk.jarvis.dao;

import com.kk.jarvis.dao.mapper.CategoryRowMapper;
import com.kk.jarvis.dto.CategoryDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/19/14
 * Time: 11:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryDao {
    private JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

   public CategoryDto addCategory(CategoryDto categoryDto) {
        String sqlQuery = "select * from user_category where user_id=? and category = ? and sub_category =?";
        List<CategoryDto> categoryDtoList = jdbcTemplate.query(sqlQuery,
                new Object[]{categoryDto.getUserId(), categoryDto.getCategory(), categoryDto.getSubCategory()},
            new CategoryRowMapper());

       if(categoryDtoList != null && categoryDtoList.size()>0) {
           return categoryDtoList.get(0);
       }

       sqlQuery = "insert into user_category(user_id, category, sub_category) values(?,?,?)";
       int result = jdbcTemplate.update(sqlQuery,
               new Object[]{categoryDto.getUserId(), categoryDto.getCategory(), categoryDto.getSubCategory()
               });

       if(result > 0)
           return categoryDto;

       return null;
   }



}
