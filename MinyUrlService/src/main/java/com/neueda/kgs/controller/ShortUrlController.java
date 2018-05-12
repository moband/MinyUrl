package com.neueda.kgs.controller;

import com.neueda.kgs.controller.dto.BaseResponse;
import com.neueda.kgs.controller.dto.NewLinkDto;
import com.neueda.kgs.controller.dto.ResolveLinkDto;
import com.neueda.kgs.controller.dto.VisitStateDto;
import com.neueda.kgs.exception.InvalidAddressException;
import com.neueda.kgs.exception.KeyNotFoundException;
import com.neueda.kgs.exception.WorkerNotFoundException;
import com.neueda.kgs.service.ShortUrlService;
import com.neueda.kgs.util.Utility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

@RestController
public class ShortUrlController {

    private ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/api/v1/{key}")
    public void expandingUrl(@PathVariable String key, HttpServletRequest request, HttpServletResponse response) throws KeyNotFoundException, IOException, InvalidAddressException {

        ResolveLinkDto dto = new ResolveLinkDto();
        dto.setBrowser(Utility.getBrowserType(request));
        dto.setOs(Utility.getOperatingSystemType(request));
        dto.setShortUrl(key);

        String longUrl = shortUrlService.resolve(dto).getLongUrl();

        response.setHeader("Location", longUrl);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    }

    @PostMapping("/api/v1/shortify")
    public ResponseEntity<BaseResponse> assignNewKey(@RequestBody NewLinkDto dto) throws  MalformedURLException, UnknownHostException {

        String key = shortUrlService.shorten(dto);
        return ResponseEntity.ok().body(new BaseResponse(true,key,BaseResponse.SUCCESSFUL));
    }

    @GetMapping("/api/v1/stat/{key}")
    public ResponseEntity<BaseResponse> getStats(@PathVariable String key) throws KeyNotFoundException {
        VisitStateDto dto = shortUrlService.getVisitStateByKey(key);
        return ResponseEntity.ok().body(dto);
    }


}
