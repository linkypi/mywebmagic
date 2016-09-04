package com.lynch.mapper;

import com.lynch.model.MultiplePrice;
import org.apache.ibatis.annotations.*;


/**
 * @author: JL
 * @version: v1.0
 * @date: 2016/9/4/0004 12:04
 */
public interface MultiplePriceMapper {
    /**
     *
     * @param username
     * @param password
     * @return
     */
   @Select("select * from t_user where username=#{un} and password=#{pw}")
   @Results({
       @Result(id=true,property="id",column="id",javaType=Integer.class),
       @Result(property="username",column="username",javaType=String.class),
       @Result(property="password",column="password",javaType=String.class),
       @Result(property="account",column="account",javaType=Double.class)
   })
   public MultiplePrice login(@Param("un")String username, @Param("pw")String password);

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Insert("insert into t_user value (null,user.username,user.password,user.account)")
    @Options(useGeneratedKeys=true,keyProperty="user.id")
    public int insertUser(@Param("user")MultiplePrice user) throws Exception;

    /**
     *
     * @param user
     * @param id
     * @return
     * @throws Exception
     */
    @Update(" update t_user set username=#{u.username},password=#{u.password},account=#{u.account} where id=#{id}")
    public int updateUser (@Param("u")MultiplePrice user,@Param("id")int id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Delete(" delete from t_user where id=#{id}  ")
    public int deleteUser(int id) throws Exception;
}
