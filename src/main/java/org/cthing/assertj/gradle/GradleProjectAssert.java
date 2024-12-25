/*
 * Copyright 2024 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.FileAssert;
import org.gradle.api.Project;
import org.gradle.api.reporting.Reporting;


/**
 * Custom AssertJ assertions for unit testing Gradle projects.
 */
@SuppressWarnings("UnusedReturnValue")
public class GradleProjectAssert extends AbstractAssert<GradleProjectAssert, Project> {

    /**
     * Constructs the assertion for the specified Gradle project.
     *
     * @param project  Gradle project to test
     */
    public GradleProjectAssert(final Project project) {
        super(project, GradleProjectAssert.class);
    }

    /**
     * Creates the assertion for the specified Gradle project.
     *
     * @param project  Gradle project to test
     * @return This assertion
     */
    public static GradleProjectAssert assertThat(final Project project) {
        return new GradleProjectAssert(project);
    }

    /**
     * Tests whether the Gradle project contains the specified extensions.
     *
     * @param extensionNames  Names of the project extensions to check
     * @return This assertion
     */
    public GradleProjectAssert hasExtension(final String... extensionNames) {
        isNotNull();

        for (final String extensionName : extensionNames) {
            if (this.actual.getExtensions().findByName(extensionName) == null) {
                failWithMessage("Project '%s' does not contain the extension '%s'", this.actual.getName(),
                                extensionName);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project does not contain the specified extensions.
     *
     * @param extensionNames  Names of the project extensions to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveExtension(final String... extensionNames) {
        isNotNull();

        for (final String extensionName : extensionNames) {
            if (this.actual.getExtensions().findByName(extensionName) != null) {
                failWithMessage("Project '%s' should not contain the extension '%s'", this.actual.getName(),
                                extensionName);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains the specified extension of the specified type.
     *
     * @param extensionName  Name of the project extensions to check
     * @param type Expected type of the extension
     * @return This assertion
     */
    public GradleProjectAssert hasExtensionWithType(final String extensionName, final Class<?> type) {
        isNotNull();

        final Object extension = this.actual.getExtensions().findByName(extensionName);
        if (extension == null) {
            failWithMessage("Project '%s' does not contain the extension '%s'", this.actual.getName(), extensionName);
        } else if (!type.isInstance(extension)) {
            failWithMessage("Expected extension '%s' to be an instance of '%s' but is '%s'", extensionName,
                            type.getName(), extension.getClass().getName());
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains an extension of the specified type.
     *
     * @param type Expected type of the extension
     * @return This assertion
     */
    public GradleProjectAssert hasExtensionWithType(final Class<?> type) {
        isNotNull();

        final Object extension = this.actual.getExtensions().findByType(type);
        if (extension == null) {
            failWithMessage("Project '%s' does not contain an extension of type '%s'", this.actual.getName(),
                            type.getName());
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains the specified configurations.
     *
     * @param configurationNames  Names of the project configurations to check
     * @return This assertion
     */
    public GradleProjectAssert hasConfiguration(final String... configurationNames) {
        isNotNull();

        for (final String configurationName : configurationNames) {
            if (this.actual.getConfigurations().findByName(configurationName) == null) {
                failWithMessage("Project '%s' does not contain the configuration '%s'", this.actual.getName(),
                                configurationName);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project does not contain the specified configurations.
     *
     * @param configurationNames  Names of the project configurations to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveConfiguration(final String... configurationNames) {
        isNotNull();

        for (final String configurationName : configurationNames) {
            if (this.actual.getConfigurations().findByName(configurationName) != null) {
                failWithMessage("Project '%s' should not contain the configuration '%s'", this.actual.getName(),
                                configurationName);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains the specified plugins.
     *
     * @param pluginIds  Identifiers of the plugins to check
     * @return This assertion
     */
    public GradleProjectAssert hasPlugin(final String... pluginIds) {
        isNotNull();

        for (final String pluginId : pluginIds) {
            if (this.actual.getPluginManager().findPlugin(pluginId) == null) {
                failWithMessage("Project '%s' does not contain the plugin '%s'", this.actual.getName(), pluginId);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project does not contain the specified plugins.
     *
     * @param pluginIds  Identifiers of the plugins to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHavePlugin(final String... pluginIds) {
        isNotNull();

        for (final String pluginId : pluginIds) {
            if (this.actual.getPluginManager().hasPlugin(pluginId)) {
                failWithMessage("Project '%s' should not contain the plugin '%s'", this.actual.getName(), pluginId);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains the specified tasks.
     *
     * @param taskNames  Names of the tasks to check
     * @return This assertion
     */
    public GradleProjectAssert hasTask(final String... taskNames) {
        isNotNull();

        for (final String taskName : taskNames) {
            if (this.actual.getTasks().findByName(taskName) == null) {
                failWithMessage("Project '%s' does not contain the task '%s'", this.actual.getName(), taskName);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project does not contain the specified tasks.
     *
     * @param taskNames  Names of the tasks to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveTask(final String... taskNames) {
        isNotNull();

        for (final String taskName : taskNames) {
            if (this.actual.getTasks().findByName(taskName) != null) {
                failWithMessage("Project '%s' should not contain the task '%s'", this.actual.getName(), taskName);
            }
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains the specified task of the specified type.
     *
     * @param taskName Name of the tasks to check
     * @param type Expected type of the task
     * @return This assertion
     */
    public GradleProjectAssert hasTaskWithType(final String taskName, final Class<?> type) {
        final Object task = this.actual.getTasks().findByName(taskName);
        if (task == null) {
            failWithMessage("Project '%s' does not contain the task '%s'", this.actual.getName(), taskName);
        } else if (!type.isInstance(task)) {
            failWithMessage("Expected task '%s' to be an instance of '%s' but is '%s'", taskName,
                            type.getName(), task.getClass().getName());
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains the specified task which implements the {@link Reporting} interface.
     *
     * @param taskName Name of the tasks to check
     * @return This assertion
     */
    public GradleProjectAssert hasTaskWithReports(final String taskName) {
        final Object task = this.actual.getTasks().findByName(taskName);
        if (task == null) {
            failWithMessage("Project '%s' does not contain the task '%s'", this.actual.getName(), taskName);
        } else if (!(task instanceof Reporting)) {
            failWithMessage("Expected task '%s' to implement 'Reporting' but does not", taskName);
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains a file with the specified pathname.
     *
     * @param pathname  File to check relative to the root of the project
     * @return This assertion
     */
    public GradleProjectAssert hasProjectFile(final String pathname) {
        isNotNull();

        new FileAssert(this.actual.file(pathname)).isFile();
        return this;
    }

    /**
     * Tests whether the Gradle project contains a directory with the specified pathname.
     *
     * @param pathname  Directory to check relative to the root of the project
     * @return This assertion
     */
    public GradleProjectAssert hasProjectDirectory(final String pathname) {
        isNotNull();

        new FileAssert(this.actual.file(pathname)).isDirectory();
        return this;
    }

    /**
     * Tests whether the Gradle project build directory contains a file with the specified pathname.
     *
     * @param pathname  File to check relative to the {@code build} directory of the project
     * @return This assertion
     */
    public GradleProjectAssert hasBuildFile(final String pathname) {
        isNotNull();

        new FileAssert(new File(this.actual.getLayout().getBuildDirectory().get().getAsFile(), pathname)).isFile();
        return this;
    }

    /**
     * Tests whether the Gradle project build directory contains a directory with the specified pathname.
     *
     * @param pathname  Directory to check relative to the {@code build} directory of the project
     * @return This assertion
     */
    public GradleProjectAssert hasBuildDirectory(final String pathname) {
        isNotNull();

        new FileAssert(new File(this.actual.getLayout().getBuildDirectory().get().getAsFile(), pathname)).isDirectory();
        return this;
    }

    /**
     * Tests whether the Gradle project contains a property with the specified name.
     *
     * @param propertyName  Name of the property to check
     * @return This assertion
     */
    public GradleProjectAssert hasProperty(final String propertyName) {
        isNotNull();

        if (!this.actual.hasProperty(propertyName)) {
            failWithMessage("Project '%s' does not contain a property named '%s'", this.actual.getName(), propertyName);
        }

        return this;
    }

    /**
     * Tests whether the Gradle project does not contain a property with the specified name.
     *
     * @param propertyName  Name of the property to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveProperty(final String propertyName) {
        isNotNull();

        if (this.actual.hasProperty(propertyName)) {
            failWithMessage("Project '%s' should not contain a property named '%s'", this.actual.getName(), propertyName);
        }

        return this;
    }

    /**
     * Tests whether the Gradle project contains a property with the specified name and the specified value.
     *
     * @param propertyName  Name of the property to check
     * @param propertyValue  Value of the property to check
     * @return This assertion
     */
    public GradleProjectAssert hasProperty(final String propertyName, final Object propertyValue) {
        hasProperty(propertyName);
        final Object actualValue = this.actual.property(propertyName);
        if (!propertyValue.equals(actualValue)) {
            final String actualValueStr = (actualValue == null) ? "null" : actualValue.toString();
            failWithMessage("Project '%s' property '%s' expected value '%s' but was '%s'",
                            this.actual.getName(), propertyName, propertyValue.toString(), actualValueStr);
        }

        return this;
    }

    /**
     * Tests whether the Gradle project does not contain a property with the specified name and the specified value.
     *
     * @param propertyName  Name of the property to check
     * @param propertyValue  Value of the property to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveProperty(final String propertyName, final Object propertyValue) {
        isNotNull();

        if (this.actual.hasProperty(propertyName)) {
            if (propertyValue.equals(this.actual.property(propertyName))) {
                failWithMessage("Project '%s' property '%s' should not equal '%s'", this.actual.getName(),
                                propertyName, propertyValue.toString());
            }
        }

        return this;
    }
}
