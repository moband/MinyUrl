package com.neueda.kgs.service;

import com.neueda.kgs.controller.dto.NewLinkDto;
import com.neueda.kgs.controller.dto.ResolveLinkDto;
import com.neueda.kgs.controller.dto.VisitStateDto;
import com.neueda.kgs.exception.InvalidAddressException;
import com.neueda.kgs.exception.KeyNotFoundException;
import com.neueda.kgs.exception.WorkerNotFoundException;
import com.neueda.kgs.model.ShortUrl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

public interface ShortUrlService {

    String shorten(NewLinkDto linkDto) throws  MalformedURLException, UnknownHostException;

    ShortUrl resolve(ResolveLinkDto dto) throws KeyNotFoundException, InvalidAddressException;

    VisitStateDto getVisitStateByKey(String key) throws KeyNotFoundException;

}
