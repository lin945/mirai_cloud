package com.lin945.mirai.rpc

import com.lin945.mirai.api.MiraiApi
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service

@FeignClient(name = "mirai-instance", path = "api")
@Service
interface MiraiMessageApi : MiraiApi {
}