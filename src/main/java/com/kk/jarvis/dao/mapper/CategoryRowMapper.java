package com.kk.jarvis.dao.mapper;

import com.kk.jarvis.dto.CategoryDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kkedari
 * Date: 5/20/14
 * Time: 1:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryRowMapper implements RowMapper<CategoryDto> {
    @Override
    public CategoryDto mapRow(ResultSet p_Rs, int p_RowNum) throws SQLException
    {
        CategoryDto l_Result = new CategoryDto();

        String category = p_Rs.getString("category");
        String subCategory = p_Rs.getString("sub_category");
        int userId = p_Rs.getInt("user_id");

        l_Result.setUserId(userId);
        l_Result.setCategory(category);
        l_Result.setSubCategory(subCategory);
        return l_Result;

    }
}
