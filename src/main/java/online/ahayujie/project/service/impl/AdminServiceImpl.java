package online.ahayujie.project.service.impl;

import online.ahayujie.project.bean.model.Admin;
import online.ahayujie.project.mapper.AdminMapper;
import online.ahayujie.project.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author aha
 * @since 2022-03-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
