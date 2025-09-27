/*
 * Copyright 2024 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.provider.Provider;
import org.gradle.testfixtures.ProjectBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.cthing.assertj.gradle.GradleAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


public class GradleConfigurationAssertTest {

    private Project project;

    @BeforeEach
    public void setup() {
        this.project = ProjectBuilder.builder().build();
    }

    @Test
    public void testInstanceFactory() {
        final Provider<@NonNull Configuration> provider = this.project.getConfigurations().register("config");
        assertThat(provider).get(GradleAssertFactories.CONFIGURATION).hasName("config");
    }

    @Test
    public void testHasName() {
        final Configuration configuration = this.project.getConfigurations().create("config");
        assertThat(configuration).hasName("config");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(configuration).hasName("foo"))
                .withMessage("Expected configuration name to be 'foo', but was 'config'");
    }

    @Test
    public void testHasDescription() {
        final Configuration configuration = this.project.getConfigurations().create("config");
        configuration.setDescription("Hello world");
        assertThat(configuration).hasDescription("Hello world");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(configuration).hasDescription("foo"))
                .withMessage("Expected configuration 'config' description to be 'foo', but was 'Hello world'");
    }

    @Test
    public void testCanBeConsumed() {
        final Configuration configuration1 = this.project.getConfigurations().create("config1");
        configuration1.setCanBeConsumed(true);
        final Configuration configuration2 = this.project.getConfigurations().create("config2");
        configuration2.setCanBeConsumed(false);

        assertThat(configuration1).canBeConsumed();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(configuration2).canBeConsumed())
                .withMessage("Expected configuration 'config2' can be consumed by other projects, but it cannot");

    }

    @Test
    @SuppressWarnings("UnstableApiUsage")
    public void testCanBeDeclared() {
        final Configuration configuration1 = this.project.getConfigurations().create("config1");
        configuration1.setCanBeDeclared(true);
        final Configuration configuration2 = this.project.getConfigurations().create("config2");
        configuration2.setCanBeDeclared(false);

        assertThat(configuration1).canBeDeclared();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(configuration2).canBeDeclared())
                .withMessage("Expected dependencies can be declared on configuration 'config2', but they cannot");

    }

    @Test
    public void testCanBeResolved() {
        final Configuration configuration1 = this.project.getConfigurations().create("config1");
        configuration1.setCanBeResolved(true);
        final Configuration configuration2 = this.project.getConfigurations().create("config2");
        configuration2.setCanBeResolved(false);

        assertThat(configuration1).canBeResolved();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(configuration2).canBeResolved())
                .withMessage("Expected configuration 'config2' can be resolved, but it cannot");

    }

    @Test
    public void testIsTransitive() {
        final Configuration configuration1 = this.project.getConfigurations().create("config1");
        configuration1.setTransitive(true);
        final Configuration configuration2 = this.project.getConfigurations().create("config2");
        configuration2.setTransitive(false);

        assertThat(configuration1).isTransitive();
        assertThat(configuration2).isNotTransitive();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(configuration1).isNotTransitive())
                .withMessage("Expected configuration 'config1' not to be transitive, but it is");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(configuration2).isTransitive())
                .withMessage("Expected configuration 'config2' to be transitive, but it is not");

    }
}
