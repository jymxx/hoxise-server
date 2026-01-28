package cn.hoxise.module.movie.service.movie;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.movie.dal.entity.MovieContentsDO;
import cn.hoxise.module.movie.dal.mapper.MovieContentsMapper;
import org.springframework.stereotype.Service;

/**
 * MovieContentsServiceImpl
 *
 * @author Hoxise
 * @since 2026/01/14 15:23:32
 */
@Service
public class MovieContentsServiceImpl extends ServiceImpl<MovieContentsMapper, MovieContentsDO>
    implements MovieContentsService{

}




