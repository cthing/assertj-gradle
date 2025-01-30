/*
 * Copyright 2024 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.api.FileAssert;
import org.assertj.core.internal.Conditions;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.reporting.Reporting;


/**
 * AssertJ assertions for unit testing a Gradle {@link Project}.
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
     * Verifies that the Gradle project contains the specified extensions.
     *
     * @param extensionName  Name of first project extension to check
     * @param extensionNames  Names of additional project extensions to check
     * @return This assertion
     */
    public GradleProjectAssert hasExtension(final String extensionName, final String... extensionNames) {
        isNotNull();

        final Consumer<String> testExtension = name -> {
            if (this.actual.getExtensions().findByName(name) == null) {
                failWithMessage("Project '%s' does not contain the extension '%s'", this.actual.getName(), name);
            }
        };

        testExtension.accept(extensionName);
        if (extensionNames.length > 0) {
            Arrays.stream(extensionNames).forEach(testExtension);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project does not contain the specified extensions.
     *
     * @param extensionName  Name of first project extension to check
     * @param extensionNames  Names of additional project extensions to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveExtension(final String extensionName, final String... extensionNames) {
        isNotNull();

        final Consumer<String> testExtension = name -> {
            if (this.actual.getExtensions().findByName(name) != null) {
                failWithMessage("Project '%s' should not contain the extension '%s'", this.actual.getName(), name);
            }
        };

        testExtension.accept(extensionName);
        if (extensionNames.length > 0) {
            Arrays.stream(extensionNames).forEach(testExtension);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains the specified extension of the specified type.
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

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains an extension of the specified type.
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

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains an extension with the specified name and type and provides it to
     * the given {@link Consumer} for further assertions.
     *
     * @param extensionsName Name of the project extension to check
     * @param <T> type of the extension
     * @param type Type of extension to check
     * @param requirement Allows further assertions on the extension
     * @return This assertion
     */
    @SuppressWarnings("unchecked")
    public <T> GradleProjectAssert hasExtensionSatisfying(final String extensionsName, final Class<T> type,
                                                          final Consumer<T> requirement) {
        hasExtensionWithType(extensionsName, type);
        requirement.accept((T)this.actual.getExtensions().getByName(extensionsName));
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains an extension with the specified name and type and which satisfies
     * the given {@link Condition}.
     *
     * @param extensionsName Name of the project extension to check
     * @param <T> type of the extension
     * @param type Type of extension to check
     * @param condition Condition to satisfy
     * @return This assertion
     */
    @SuppressWarnings("unchecked")
    public <T> GradleProjectAssert hasExtensionSatisfying(final String extensionsName, final Class<T> type,
                                                          final Condition<T> condition) {
        hasExtensionWithType(extensionsName, type);
        Conditions.instance().assertIs(this.info, (T)this.actual.getExtensions().getByName(extensionsName), condition);
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains an extension with the specified type and provides it to
     * the given {@link Consumer} for further assertions.
     *
     * @param <T> type of the extension
     * @param type Type of extension to check
     * @param requirement Allows further assertions on the extension
     * @return This assertion
     */
    public <T> GradleProjectAssert hasExtensionSatisfying(final Class<T> type, final Consumer<T> requirement) {
        hasExtensionWithType(type);
        requirement.accept(this.actual.getExtensions().getByType(type));
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains an extension with the specified type and which satisfies
     * the given {@link Condition}.
     *
     * @param <T> type of the extension
     * @param type Type of extension to check
     * @param condition Condition to satisfy
     * @return This assertion
     */
    public <T> GradleProjectAssert hasExtensionSatisfying(final Class<T> type, final Condition<T> condition) {
        hasExtensionWithType(type);
        Conditions.instance().assertIs(this.info, this.actual.getExtensions().getByType(type), condition);
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains the specified configurations.
     *
     * @param configurationName Name of the first project configuration to check
     * @param configurationNames Names of additional project configurations to check
     * @return This assertion
     */
    public GradleProjectAssert hasConfiguration(final String configurationName, final String... configurationNames) {
        isNotNull();

        final Consumer<String> testConfiguration = name -> {
            if (this.actual.getConfigurations().findByName(name) == null) {
                failWithMessage("Project '%s' does not contain the configuration '%s'", this.actual.getName(), name);
            }
        };

        testConfiguration.accept(configurationName);
        if (configurationNames.length > 0) {
            Arrays.stream(configurationNames).forEach(testConfiguration);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project does not contain the specified configurations.
     *
     * @param configurationName Name of the first project configuration to check
     * @param configurationNames Names of additional project configurations to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveConfiguration(final String configurationName,
                                                        final String... configurationNames) {
        isNotNull();

        final Consumer<String> testConfiguration = name -> {
            if (this.actual.getConfigurations().findByName(name) != null) {
                failWithMessage("Project '%s' should not contain the configuration '%s'", this.actual.getName(),
                                name);
            }
        };

        testConfiguration.accept(configurationName);
        if (configurationNames.length > 0) {
            Arrays.stream(configurationNames).forEach(testConfiguration);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains a configuration with the specified name and provides it to the given
     * {@link Consumer} for further assertions.
     *
     * @param configurationName Name of the project configuration to check
     * @param requirement Allows further assertions on the configuration
     * @return This assertion
     */
    public GradleProjectAssert hasConfigurationSatisfying(final String configurationName,
                                                          final Consumer<Configuration> requirement) {
        hasConfiguration(configurationName);
        requirement.accept(this.actual.getConfigurations().getByName(configurationName));
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains a configuration with the specified name and which satisfies
     * the given {@link Condition}.
     *
     * @param configurationName Name of the project configuration to check
     * @param condition Condition to satisfy
     * @return This assertion
     */
    public GradleProjectAssert hasConfigurationSatisfying(final String configurationName,
                                                          final Condition<? super Configuration> condition) {
        hasConfiguration(configurationName);
        Conditions.instance().assertIs(this.info, this.actual.getConfigurations().getByName(configurationName),
                                       condition);
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains the specified plugins.
     *
     * @param pluginId  Identifier of first plugin to check
     * @param pluginIds  Identifiers of additional plugins to check
     * @return This assertion
     */
    public GradleProjectAssert hasPlugin(final String pluginId, final String... pluginIds) {
        isNotNull();

        final Consumer<String> testPlugin = id -> {
            if (this.actual.getPluginManager().findPlugin(id) == null) {
                failWithMessage("Project '%s' does not contain the plugin '%s'", this.actual.getName(), id);
            }
        };

        testPlugin.accept(pluginId);
        if (pluginIds.length > 0) {
            Arrays.stream(pluginIds).forEach(testPlugin);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project does not contain the specified plugins.
     *
     * @param pluginId  Identifier of first plugin to check
     * @param pluginIds  Identifiers of additional plugins to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHavePlugin(final String pluginId, final String... pluginIds) {
        isNotNull();

        final Consumer<String> testPlugin = id -> {
            if (this.actual.getPluginManager().hasPlugin(id)) {
                failWithMessage("Project '%s' should not contain the plugin '%s'", this.actual.getName(), id);
            }
        };

        testPlugin.accept(pluginId);
        if (pluginIds.length > 0) {
            Arrays.stream(pluginIds).forEach(testPlugin);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains the specified tasks.
     *
     * @param taskName Name of the first task to check
     * @param taskNames Names of additional tasks to check
     * @return This assertion
     */
    public GradleProjectAssert hasTask(final String taskName, final String... taskNames) {
        isNotNull();

        final Consumer<String> testTask = name -> {
            if (this.actual.getTasks().findByName(name) == null) {
                failWithMessage("Project '%s' does not contain the task '%s'", this.actual.getName(), name);
            }
        };

        testTask.accept(taskName);
        if (taskNames.length > 0) {
            Arrays.stream(taskNames).forEach(testTask);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project does not contain the specified tasks.
     *
     * @param taskName Name of the first task to check
     * @param taskNames Names of additional tasks to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveTask(final String taskName, final String... taskNames) {
        isNotNull();

        final Consumer<String> testTask = name -> {
            if (this.actual.getTasks().findByName(name) != null) {
                failWithMessage("Project '%s' should not contain the task '%s'", this.actual.getName(), name);
            }
        };

        testTask.accept(taskName);
        if (taskNames.length > 0) {
            Arrays.stream(taskNames).forEach(testTask);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains the specified task of the specified type.
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

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains the specified task which implements the {@link Reporting} interface.
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

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains a task with the specified name and provides it to
     * the given {@link Consumer} for further assertions.
     *
     * @param taskName Name of the task to check
     * @param requirement Allows further assertions on the task
     * @return This assertion
     */
    public GradleProjectAssert hasTaskSatisfying(final String taskName, final Consumer<Task> requirement) {
        hasTask(taskName);
        requirement.accept(this.actual.getTasks().getByName(taskName));
        return this.myself;

    }

    /**
     * Verifies that the Gradle project contains a task with the specified name and type and provides it to
     * the given {@link Consumer} for further assertions.
     *
     * @param taskName Name of the task to check
     * @param <T> Type of the task to check
     * @param type Type of the task to check
     * @param requirement Allows further assertions on the task
     * @return This assertion
     */
    @SuppressWarnings("unchecked")
    public <T extends Task> GradleProjectAssert hasTaskSatisfying(final String taskName, final Class<T> type,
                                                                  final Consumer<T> requirement) {
        hasTaskWithType(taskName, type);
        requirement.accept((T)this.actual.getTasks().getByName(taskName));
        return this.myself;

    }

    /**
     * Verifies that the Gradle project contains a task with the specified name and which satisfies
     * the given {@link Condition}.
     *
     * @param taskName Name of the task to check
     * @param condition Condition to satisfy
     * @return This assertion
     */
    public GradleProjectAssert hasTaskSatisfying(final String taskName, final Condition<? super Task> condition) {
        hasTask(taskName);
        Conditions.instance().assertIs(this.info, this.actual.getTasks().getByName(taskName), condition);
        return this.myself;

    }

    /**
     * Verifies that the Gradle project contains a task with the specified name and type and which satisfies
     * the given {@link Condition}.
     *
     * @param taskName Name of the task to check
     * @param <T> Type of the task to check
     * @param type Type of the task to check
     * @param condition Condition to satisfy
     * @return This assertion
     */
    @SuppressWarnings("unchecked")
    public <T extends Task> GradleProjectAssert hasTaskSatisfying(final String taskName, final Class<T> type,
                                                                  final Condition<? super Task> condition) {
        hasTaskWithType(taskName, type);
        Conditions.instance().assertIs(this.info, (T)this.actual.getTasks().getByName(taskName), condition);
        return this.myself;

    }

    /**
     * Verifies that the Gradle project contains a file with the specified pathname.
     *
     * @param pathname  File to check relative to the root of the project
     * @return This assertion
     */
    public GradleProjectAssert hasProjectFile(final String pathname) {
        isNotNull();

        new FileAssert(this.actual.file(pathname)).isFile();
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains a directory with the specified pathname.
     *
     * @param pathname  Directory to check relative to the root of the project
     * @return This assertion
     */
    public GradleProjectAssert hasProjectDirectory(final String pathname) {
        isNotNull();

        new FileAssert(this.actual.file(pathname)).isDirectory();
        return this.myself;
    }

    /**
     * Verifies that the Gradle project build directory contains a file with the specified pathname.
     *
     * @param pathname  File to check relative to the {@code build} directory of the project
     * @return This assertion
     */
    public GradleProjectAssert hasBuildFile(final String pathname) {
        isNotNull();

        new FileAssert(new File(this.actual.getLayout().getBuildDirectory().get().getAsFile(), pathname)).isFile();
        return this.myself;
    }

    /**
     * Verifies that the Gradle project build directory contains a directory with the specified pathname.
     *
     * @param pathname  Directory to check relative to the {@code build} directory of the project
     * @return This assertion
     */
    public GradleProjectAssert hasBuildDirectory(final String pathname) {
        isNotNull();

        new FileAssert(new File(this.actual.getLayout().getBuildDirectory().get().getAsFile(), pathname)).isDirectory();
        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains a property with the specified name.
     *
     * @param propertyName  Name of the property to check
     * @return This assertion
     */
    public GradleProjectAssert hasProperty(final String propertyName) {
        isNotNull();

        if (!this.actual.hasProperty(propertyName)) {
            failWithMessage("Project '%s' does not contain a property named '%s'", this.actual.getName(), propertyName);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project does not contain a property with the specified name.
     *
     * @param propertyName  Name of the property to check
     * @return This assertion
     */
    public GradleProjectAssert doesNotHaveProperty(final String propertyName) {
        isNotNull();

        if (this.actual.hasProperty(propertyName)) {
            failWithMessage("Project '%s' should not contain a property named '%s'", this.actual.getName(), propertyName);
        }

        return this.myself;
    }

    /**
     * Verifies that the Gradle project contains a property with the specified name and the specified value.
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

        return this.myself;
    }

    /**
     * Verifies that the Gradle project does not contain a property with the specified name and the specified value.
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

        return this.myself;
    }
}
