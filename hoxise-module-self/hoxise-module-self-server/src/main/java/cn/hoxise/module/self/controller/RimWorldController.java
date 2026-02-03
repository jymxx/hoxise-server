package cn.hoxise.module.self.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环世界工具
 *
 * @author hoxise
 * @since 2026/2/3 上午9:37
 */
@RestController
@RequestMapping("/self/rw")
public class RimWorldController {

    @Operation(summary = "翻译")
    @RequestMapping("/translate")
    public void translate(MultipartFile file){

    }

}
