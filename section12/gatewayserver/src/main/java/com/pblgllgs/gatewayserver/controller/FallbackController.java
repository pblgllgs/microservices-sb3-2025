package com.pblgllgs.gatewayserver.controller;
/*
 *
 * @author pblgl
 * Created on 03-04-2025
 *
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport(){
        return Mono.just("ha occurrido un error. Por favor contacta al equipo de soporte");
    }
}
