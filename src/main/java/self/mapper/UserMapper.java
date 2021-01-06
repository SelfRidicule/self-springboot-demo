package self.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import self.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE name = #{name}")
    User findByName(@Param("name") String name);

    @Select("SELECT * FROM user")
    List<User> list();

    @Select("insert into user(name, password) " +
            "values(#{name}, #{password})")
    void save(@Param("name") String name, @Param("password") String password);
}
