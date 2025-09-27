/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.gradle.api.artifacts.Configuration;


/**
 * AssertJ assertions for unit testing a Gradle {@link Configuration}.
 */
@SuppressWarnings("UnusedReturnValue")
public class GradleConfigurationAssert
        extends AbstractGradleFileCollectionAssert<GradleConfigurationAssert, Configuration> {

    /**
     * Constructs the assertion for the specified Gradle configuration.
     *
     * @param configuration Gradle configuration to test
     */
    public GradleConfigurationAssert(final Configuration configuration) {
        super(configuration);
    }

    /**
     * Creates the assertion for the specified Gradle configuration.
     *
     * @param configuration  Gradle configuration to test
     * @return This assertion
     */
    public static GradleConfigurationAssert assertThat(final Configuration configuration) {
        return new GradleConfigurationAssert(configuration);
    }

    /**
     * Verifies that the configuration has the specified name.
     *
     * @param name Expected configuration name
     * @return This assertion
     */
    public GradleConfigurationAssert hasName(final String name) {
        isNotNull();

        if (!name.equals(this.actual.getName())) {
            failWithMessage("Expected configuration name to be '%s', but was '%s'", name, this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the configuration has the specified description.
     *
     * @param description Expected configuration description
     * @return This assertion
     */
    public GradleConfigurationAssert hasDescription(final String description) {
        isNotNull();

        if (!description.equals(this.actual.getDescription())) {
            failWithMessage("Expected configuration '%s' description to be '%s', but was '%s'", this.actual.getName(),
                    description, this.actual.getDescription());
        }

        return this.myself;
    }

    /**
     * Verifies that the configuration can be consumed by other projects.
     *
     * @return This assertion
     */
    public GradleConfigurationAssert canBeConsumed() {
        isNotNull();

        if (!this.actual.isCanBeConsumed()) {
            failWithMessage("Expected configuration '%s' can be consumed by other projects, but it cannot",
                    this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that dependencies can be declared on the configuration.
     *
     * @return This assertion
     */
    @SuppressWarnings("UnstableApiUsage")
    public GradleConfigurationAssert canBeDeclared() {
        isNotNull();

        if (!this.actual.isCanBeDeclared()) {
            failWithMessage("Expected dependencies can be declared on configuration '%s', but they cannot",
                    this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the configuration can be resolved.
     *
     * @return This assertion
     */
    public GradleConfigurationAssert canBeResolved() {
        isNotNull();

        if (!this.actual.isCanBeResolved()) {
            failWithMessage("Expected configuration '%s' can be resolved, but it cannot", this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the configuration is transitive.
     *
     * @return This assertion
     */
    public GradleConfigurationAssert isTransitive() {
        isNotNull();

        if (!this.actual.isTransitive()) {
            failWithMessage("Expected configuration '%s' to be transitive, but it is not", this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the configuration is not transitive.
     *
     * @return This assertion
     */
    public GradleConfigurationAssert isNotTransitive() {
        isNotNull();

        if (this.actual.isTransitive()) {
            failWithMessage("Expected configuration '%s' not to be transitive, but it is", this.actual.getName());
        }

        return this.myself;
    }
}
