package cn.hoxise.self.movie.service.movie;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.movie.dal.entity.MovieContentsDO;
import cn.hoxise.self.movie.dal.mapper.MovieContentsMapper;
import org.springframework.stereotype.Service;

/**
* @author Hoxise
* @description 针对表【movie_contents】的数据库操作Service实现
* @createDate 2025-12-22 07:34:01
*/
@Service
public class MovieContentsServiceImpl extends ServiceImpl<MovieContentsMapper, MovieContentsDO>
    implements MovieContentsService{

}




