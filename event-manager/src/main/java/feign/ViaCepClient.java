package feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public class ViaCepClient {
    @GetMapping("/{cep}/json")
    Map<String, String> getCepDetails(@PathVariable("cep") String cep);
    //tenho que corrigir esta classe, está dando erro!//
}
