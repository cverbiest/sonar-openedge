/*
 * OpenEdge DB plugin for SonarQube
 * Copyright (C) 2015 Riverside Software
 * contact AT riverside DASH software DOT fr
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.openedge;

import java.util.Arrays;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.openedge.api.AnnotationBasedRulesDefinition;

public class OpenEdgeExtraRulesDefinition implements RulesDefinition {
  // Copied from OpenEdge.java
  private static final String KEY = "oe";

  public static final String REPOSITORY_KEY = "rssw-samples";
  public static final String REPOSITORY_NAME = "Riverside Software sample rules";

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPOSITORY_KEY, KEY).setName(REPOSITORY_NAME);

    AnnotationBasedRulesDefinition annotationLoader = new AnnotationBasedRulesDefinition(repository, KEY);
    annotationLoader.addRuleClasses(false, false, Arrays.<Class> asList(OpenEdgeExtraRulesRegistrar.ppCheckClasses()));
    annotationLoader.addRuleClasses(false, false, Arrays.<Class> asList(OpenEdgeExtraRulesRegistrar.xrefCheckClasses()));

    repository.done();
  }
}