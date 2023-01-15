package com.community.rest.controller;

import com.community.rest.domain.Board;
import com.community.rest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RepositoryRestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardRepository boardRepository;

    @GetMapping("/boards")
    @ResponseBody
    public Resources<Board> simpleBoard(@PageableDefault Pageable pageable) {
        Page<Board> boardList = boardRepository.findAll(pageable);

        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boardList.getNumber(), boardList.getTotalElements());
        Resources<Board> resources = new PagedResources<>(boardList.getContent(), pageMetadata);
        resources.add(linkTo(methodOn(BoardRestController.class).simpleBoard(pageable)).withSelfRel());
        return resources;
    }
}
