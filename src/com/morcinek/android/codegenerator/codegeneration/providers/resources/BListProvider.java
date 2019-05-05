package com.morcinek.android.codegenerator.codegeneration.providers.resources;

import com.google.common.collect.Sets;
import com.morcinek.android.codegenerator.extractor.model.Resource;

import java.util.Set;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class BListProvider extends AbstractResourceProvider {

    public BListProvider(Resource resource) {
        super(resource);
    }

    @Override
    public Set<String> provideInterface() {
        return null;
    }

    @Override
    public Set<String> provideAssignment() {
        return null;
    }

    @Override
    public Set<String> provideField() {
        return Sets.newHashSet("B");
    }

    @Override
    public Set<String> provideMethod() {
        return Sets.newHashSet("BList");
    }
}
