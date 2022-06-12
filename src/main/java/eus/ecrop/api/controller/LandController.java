package eus.ecrop.api.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eus.ecrop.api.domain.Land;
import eus.ecrop.api.domain.User;
import eus.ecrop.api.dto.LandDto;
import eus.ecrop.api.dto.ValidationGroup.Create;
import eus.ecrop.api.dto.ValidationGroup.Update;
import eus.ecrop.api.service.LandService;

/*
* @author Mikel Orobengoa
* @version 26/05/2022
*/

@RestController
@RequestMapping(path = "land", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class LandController {

    @Autowired
    private LandService landService;

    @GetMapping("current/all")
    public ResponseEntity<?> getCurrentUserLands(Authentication authentication,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (size == null || size < 0) {
            size = 10;
        }

        if (page == null || page < 0) {
            page = 0;
        }

        User user = (User) authentication.getPrincipal();
        Page<Land> lands = landService.findAllByUser(user.getId(), page, size);

        try {
            Page<LandDto> landDtos = new PageImpl<LandDto>(
                    lands.getContent().stream().map(land -> landService.convertToDto(land))
                            .collect(Collectors.toList()),
                    lands.getPageable(), lands.getTotalElements());
            return new ResponseEntity<>(landDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("current")
    public ResponseEntity<?> getCurrentUserLandById(Authentication authentication, @RequestParam("id") Long id,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) authentication.getPrincipal();
        Land land = landService.findById(id);

        if (land == null || !land.getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(landService.convertToDto(land), HttpStatus.OK);
    }

    @PostMapping(path = "create")
    public ResponseEntity<?> create(Authentication authentication,
            @RequestBody @Validated(Create.class) LandDto landDto, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Land land = landService.create(landDto, user);
        land = landService.save(land);

        return new ResponseEntity<>(landService.convertToDto(land), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> update(Authentication authentication,
            @RequestBody @Validated(Update.class) LandDto landDto, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        User user = (User) authentication.getPrincipal();
        Land land = landService.update(landDto, user);

        if (land == null || !land.getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        land = landService.save(land);

        return new ResponseEntity<>(landService.convertToDto(land), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(Authentication authentication, @RequestParam("id") Long id,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) authentication.getPrincipal();
        Land land = landService.findById(id);

        if (land == null || !land.getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        landService.delete(land);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}