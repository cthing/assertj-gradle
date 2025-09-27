/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.testfixtures.ProjectBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.cthing.assertj.gradle.GradleAssertions.assertThat;


public class GradleAssertionsTest {

    private Project project;

    @BeforeEach
    public void setup() {
        this.project = ProjectBuilder.builder().build();
        this.project.getPluginManager().apply("java");
        this.project.getPluginManager().apply("checkstyle");
    }

    @Test
    public void testHasConfiguration() {
        assertThat(this.project).doesNotHaveConfiguration("testConfig");
        this.project.getConfigurations().create("testConfig");
        assertThat(this.project).hasConfiguration("testConfig");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasConfiguration("_not_found_"));
    }

    @Test
    public void testIsPresent() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).isPresent();

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).isPresent();
    }
}
