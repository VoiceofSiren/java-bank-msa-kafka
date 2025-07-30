package org.example.userservice.infrastructure.repository.read;

import lombok.RequiredArgsConstructor;
import org.example.userservice.domain.model.UserReadView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserReadViewRepositoryCustomImpl implements UserReadViewRepositoryCustom{

    private final R2dbcEntityTemplate template;

    @Override
    public Flux<UserReadView> findAllUsersLimitedByPageable(Pageable pageable) {

        Query query = Query.empty()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return template
                .select(UserReadView.class)
                .matching(query)
                .all();
    }
}
