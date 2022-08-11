package com.noelog.api.repository;

import com.noelog.api.domain.entity.Post;
import com.noelog.api.domain.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
