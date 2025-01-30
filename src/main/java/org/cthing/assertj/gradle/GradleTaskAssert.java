/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import org.assertj.core.api.AbstractAssert;
import org.gradle.api.Task;


/**
 * AssertJ assertions for unit testing a Gradle {@link Task}.
 */
@SuppressWarnings("UnusedReturnValue")
public class GradleTaskAssert extends AbstractAssert<GradleTaskAssert, Task> {

    /**
     * Constructs the assertion for the specified Gradle task.
     *
     * @param task  Gradle task to test
     */
    public GradleTaskAssert(final Task task) {
        super(task, GradleTaskAssert.class);
    }

    /**
     * Creates the assertion for the specified Gradle task.
     *
     * @param task Gradle task to test
     * @return This assertion
     */
    public static GradleTaskAssert assertThat(final Task task) {
        return new GradleTaskAssert(task);
    }

    /**
     * Verifies the task has the specified name.
     *
     * @param name Expected name of the task
     * @return This assertion
     */
    public GradleTaskAssert hasName(final String name) {
        isNotNull();

        if (!this.actual.getName().equals(name)) {
            failWithMessage("Expected task to have name '%s', but it was '%s'", name, this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies the task has the specified description.
     *
     * @param description Expected description of the task
     * @return This assertion
     */
    public GradleTaskAssert hasDescription(final String description) {
        isNotNull();

        if (!description.equals(this.actual.getDescription())) {
            failWithMessage("Expected task '%s' to have description '%s', but it was '%s'", this.actual.getName(),
                            description, this.actual.getDescription());
        }

        return this.myself;
    }

    /**
     * Verifies the task belongs to the specified group.
     *
     * @param group Expected group for the task
     * @return This assertion
     */
    public GradleTaskAssert hasGroup(final String group) {
        isNotNull();

        if (!group.equals(this.actual.getGroup())) {
            failWithMessage("Expected task '%s' to belong to group '%s', but was '%s'", this.actual.getName(), group,
                            this.actual.getGroup());
        }

        return this.myself;
    }

    /**
     * Verifies the task has the specified path.
     *
     * @param path Expected path of the task
     * @return This assertion
     */
    public GradleTaskAssert hasPath(final String path) {
        isNotNull();

        if (!path.equals(this.actual.getPath())) {
            failWithMessage("Expected task '%s' to have path '%s', but was '%s'", this.actual.getName(), path,
                            this.actual.getPath());
        }

        return this.myself;
    }

    /**
     * Verifies that the task is enabled.
     *
     * @return This assertion
     */
    public GradleTaskAssert isEnabled() {
        isNotNull();

        if (!this.actual.getEnabled()) {
            failWithMessage("Expected task '%s' to be enabled, but it was disabled", this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the task is disabled.
     *
     * @return This assertion
     */
    public GradleTaskAssert isDisabled() {
        isNotNull();

        if (this.actual.getEnabled()) {
            failWithMessage("Expected task '%s' to be disabled, but it was enabled", this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the task has a property with the specified name.
     *
     * @param propertyName Name of the property to check
     * @return This assertion
     */
    public GradleTaskAssert hasProperty(final String propertyName) {
        isNotNull();

        if (!this.actual.hasProperty(propertyName)) {
            failWithMessage("Expected task '%s' to have property '%s', but it does not", this.actual.getName(),
                            propertyName);
        }

        return this.myself;
    }

    /**
     * Verifies that the task has a property with the specified name with the specified value.
     *
     * @param propertyName Name of the property to check
     * @param propertyValue Expected value of the property
     * @return This assertion
     */
    public GradleTaskAssert hasPropertyValue(final String propertyName, final Object propertyValue) {
        hasProperty(propertyName);

        if (!Objects.equals(propertyValue, this.actual.property(propertyName))) {
            failWithMessage("Expected task '%s' to have property '%s' with value '%s', but is does not",
                            this.actual.getName(), propertyName, propertyValue.toString());
        }

        return this.myself;
    }

    /**
     * Verifies that the task has the specified dependencies. A
     * <a href="https://docs.gradle.org/current/javadoc/org/gradle/api/Task.html#dependencies">number of different
     * types</a> can be specified as dependencies.
     *
     * @param dependency First task dependency
     * @param dependencies Additional task dependencies
     * @return This assertion
     */
    public GradleTaskAssert dependsOn(final Object dependency, final Object... dependencies) {
        isNotNull();

        final Consumer<Object> testDependOn = dep -> {
            if (!this.actual.getDependsOn().contains(dep)) {
                failWithMessage("Expected task '%s' to depend on '%s', but it does not", this.actual.getName(),
                                dep.toString());
            }
        };

        testDependOn.accept(dependency);
        if (dependencies.length > 0) {
            Arrays.stream(dependencies).forEach(testDependOn);
        }

        return this.myself;
    }

    /**
     * Verifies that the task defines inputs.
     *
     * @return This assertion
     */
    public GradleTaskAssert hasInputs() {
        isNotNull();

        if (!this.actual.getInputs().getHasInputs()) {
            failWithMessage("Expected task '%s' to have inputs, but it does not", this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the task defines inputs and provides a file collection assertion for further verification.
     *
     * @return File collection assertion
     */
    public GradleFileCollectionAssert getInputFiles() {
        hasInputs();
        return new GradleFileCollectionAssert(this.actual.getInputs().getFiles());
    }

    /**
     * Verifies that the task defines outputs.
     *
     * @return This assertion
     */
    public GradleTaskAssert hasOutputs() {
        isNotNull();

        if (!this.actual.getOutputs().getHasOutput()) {
            failWithMessage("Expected task '%s' to have outputs, but it does not", this.actual.getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the task defines outputs and provides a file collection assertion for further verification.
     *
     * @return File collection assertion
     */
    public GradleFileCollectionAssert getOutputFiles() {
        hasOutputs();
        return new GradleFileCollectionAssert(this.actual.getOutputs().getFiles());
    }
}
