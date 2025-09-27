/*
 * Copyright 2024 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.assertj.core.api.Condition;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.provider.Provider;
import org.gradle.testfixtures.ProjectBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.cthing.assertj.gradle.GradleAssertions.assertThat;


public class GradleProjectAssertTest {

    public static class TestExtension {
        private String someValue = "foo";

        public String getSomeValue() {
            return this.someValue;
        }

        public void setSomeValue(final String someValue) {
            this.someValue = someValue;
        }
    }


    private Project project;
    private Provider<@NonNull Project> provider;

    @BeforeEach
    public void setup() {
        this.project = ProjectBuilder.builder().build();
        this.project.getPluginManager().apply("java");
        this.project.getPluginManager().apply("checkstyle");

        this.provider = this.project.getProviders().provider(() -> this.project);
    }

    @Test
    public void testCreation() {
        assertThat(this.project).isNotNull();
    }

    @Test
    public void testInstanceFactory() {
        assertThat(this.provider).get(GradleAssertFactories.PROJECT).hasPlugin("java");
    }

    @Test
    public void testHasExtension() {
        assertThat(this.project).doesNotHaveExtension("testExtension");
        this.project.getExtensions().create("testExtension", TestExtension.class);
        assertThat(this.project).hasExtension("testExtension");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtension("_not_found_"));
    }

    @Test
    public void testHasExtensionWithNameType() {
        this.project.getExtensions().create("testExtension", TestExtension.class);
        assertThat(this.project).hasExtensionWithType("testExtension", TestExtension.class);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionWithType("_not_found_", TestExtension.class));
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionWithType("testExtension", String.class));
    }

    @Test
    public void testHasExtensionWithType() {
        this.project.getExtensions().create("testExtension", TestExtension.class);
        assertThat(this.project).hasExtensionWithType(TestExtension.class);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionWithType(String.class));
    }

    @Test
    public void testHasExtensionWithNameTypeSatisfyingConsumer() {
        this.project.getExtensions().create("testExtension", TestExtension.class);
        assertThat(this.project).hasExtensionSatisfying("testExtension", TestExtension.class,
                                                        extension -> assertThat(extension.someValue).isEqualTo("foo"));
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionSatisfying("testExtension", TestExtension.class,
                                        extension -> assertThat(extension.someValue).isEqualTo("bar")));
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionSatisfying("notfound", TestExtension.class,
                                        extension -> assertThat(extension.someValue).isEqualTo("foo")));
    }

    @Test
    public void testHasExtensionWithNameTypeSatisfyingCondition() {
        final Condition<TestExtension> condition1 =
                new Condition<>(ext -> "foo".equals(ext.getSomeValue()), "Test condition");
        final Condition<TestExtension> condition2 =
                new Condition<>(ext -> "bar".equals(ext.getSomeValue()), "Test condition");

        this.project.getExtensions().create("testExtension", TestExtension.class);
        assertThat(this.project).hasExtensionSatisfying("testExtension", TestExtension.class, condition1);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionSatisfying("testExtension", TestExtension.class, condition2));
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionSatisfying("notfound", TestExtension.class, condition1));
    }

    @Test
    public void testHasExtensionWithTypeSatisfyingConsumer() {
        this.project.getExtensions().create("testExtension", TestExtension.class);
        assertThat(this.project).hasExtensionSatisfying(TestExtension.class,
                                                        extension -> assertThat(extension.someValue).isEqualTo("foo"));
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionSatisfying(TestExtension.class,
                                        extension -> assertThat(extension.someValue).isEqualTo("bar")));
    }

    @Test
    public void testHasExtensionWithTypeSatisfyingCondition() {
        final Condition<TestExtension> condition1 =
                new Condition<>(ext -> "foo".equals(ext.getSomeValue()), "Test condition");
        final Condition<TestExtension> condition2 =
                new Condition<>(ext -> "bar".equals(ext.getSomeValue()), "Test condition");

        this.project.getExtensions().create("testExtension", TestExtension.class);
        assertThat(this.project).hasExtensionSatisfying(TestExtension.class, condition1);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasExtensionSatisfying(TestExtension.class, condition2));
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
    public void testHasConfigurationSatisfyingConsumer() {
        assertThat(this.project).doesNotHaveConfiguration("testConfig");
        final Configuration config = this.project.getConfigurations().create("testConfig");
        config.setDescription("Hello world");
        assertThat(this.project).hasConfigurationSatisfying("testConfig",
                                                            conf -> assertThat(conf).hasDescription("Hello world"));
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project)
                        .hasConfigurationSatisfying("foo", conf -> assertThat(conf).hasDescription("Hello world")))
                .withMessage("Project 'test' does not contain the configuration 'foo'");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project)
                        .hasConfigurationSatisfying("testConfig", conf -> assertThat(conf).hasDescription("foo")))
                .withMessage("Expected configuration 'testConfig' description to be 'foo', but was 'Hello world'");
    }

    @Test
    public void testHasConfigurationSatisfyingCondition() {
        final Condition<Configuration> condition1 =
                new Condition<>(config -> "Hello world".equals(config.getDescription()), "Test condition");
        final Condition<Configuration> condition2 =
                new Condition<>(config -> "foo".equals(config.getDescription()), "Test condition");

        assertThat(this.project).doesNotHaveConfiguration("testConfig");
        final Configuration config = this.project.getConfigurations().create("testConfig");
        config.setDescription("Hello world");
        assertThat(this.project).hasConfigurationSatisfying("testConfig", condition1);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasConfigurationSatisfying("foo", condition1))
                .withMessage("Project 'test' does not contain the configuration 'foo'");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasConfigurationSatisfying("testConfig", condition2))
                .withMessage("\nExpecting actual:\n  configuration ':testConfig'\nto be Test condition");
    }

    @Test
    public void testHasPlugin() {
        assertThat(this.project).hasPlugin("java");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasPlugin("_not_found_"));
        assertThat(this.project).doesNotHavePlugin("_not_found_");
    }

    @Test
    public void testHasTask() {
        assertThat(this.project).hasTask("build");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasTask("_not_found_"));
        assertThat(this.project).doesNotHaveTask("_not_found_");
    }

    @Test
    public void testHasTaskWithType() {
        assertThat(this.project).hasTaskWithType("build", DefaultTask.class);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasTaskWithType("_not_found_", DefaultTask.class));
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasTaskWithType("build", String.class));
    }

    @Test
    public void testHasTaskWithReports() {
        assertThat(this.project).hasTaskWithReports("checkstyleMain");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasTaskWithReports("build"));
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasTaskWithReports("_not_found_"));
    }

    @Test
    public void testHasTaskSatisfyingConsumer() {
        assertThat(this.project).hasTaskSatisfying("build", task -> assertThat(task).hasName("build"));
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("build", task -> assertThat(task).hasName("foo")))
                .withMessage("Expected task to have name 'foo', but it was 'build'");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("foo", task -> assertThat(task).hasName("build")))
                .withMessage("Project 'test' does not contain the task 'foo'");
    }

    @Test
    public void testHasTaskWithTypeSatisfyingConsumer() {
        assertThat(this.project).hasTaskSatisfying("build", DefaultTask.class,
                                                   task -> assertThat(task).hasName("build"));
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("build", DefaultTask.class,
                                                                             task -> assertThat(task).hasName("foo")))
                .withMessage("Expected task to have name 'foo', but it was 'build'");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("foo", DefaultTask.class,
                                                                             task -> assertThat(task).hasName("build")))
                .withMessage("Project 'test' does not contain the task 'foo'");
    }

    @Test
    public void testHasTaskSatisfyingCondition() {
        final Condition<Task> condition1 =
                new Condition<>(task -> "build".equals(task.getName()), "Test condition");
        final Condition<Task> condition2 =
                new Condition<>(task -> "foo".equals(task.getName()), "Test condition");
        assertThat(this.project).hasTaskSatisfying("build", condition1);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("build", condition2))
                .withMessage("\nExpecting actual:\n  task ':build'\nto be Test condition");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("foo", condition1))
                .withMessage("Project 'test' does not contain the task 'foo'");
    }

    @Test
    public void testHasTaskWithTypeSatisfyingCondition() {
        final Condition<Task> condition1 =
                new Condition<>(task -> "build".equals(task.getName()), "Test condition");
        final Condition<Task> condition2 =
                new Condition<>(task -> "foo".equals(task.getName()), "Test condition");
        assertThat(this.project).hasTaskSatisfying("build", DefaultTask.class, condition1);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("build", DefaultTask.class, condition2))
                .withMessage("\nExpecting actual:\n  task ':build'\nto be Test condition");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.project).hasTaskSatisfying("foo", DefaultTask.class, condition1))
                .withMessage("Project 'test' does not contain the task 'foo'");
    }

    @Test
    public void testHasProjectFile() throws IOException {
        final File dir = this.project.file("src/main");
        assertThat(dir.mkdirs()).isTrue();
        Files.writeString(dir.toPath().resolve("foo.txt"), "Hello");
        assertThat(this.project).hasProjectFile("src/main/foo.txt");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasProjectFile("_not_found_"));
    }

    @Test
    public void testHasProjectDirectory() {
        final File dir = this.project.file("src/main");
        assertThat(dir.mkdirs()).isTrue();
        assertThat(this.project).hasProjectDirectory("src/main");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasProjectDirectory("_not_found_"));
    }

    @Test
    public void testHasBuildFile() throws IOException {
        final File buildDir = this.project.getLayout().getBuildDirectory().get().getAsFile();
        assertThat(buildDir.mkdirs()).isTrue();
        Files.writeString(buildDir.toPath().resolve("foo.txt"), "Hello");
        assertThat(this.project).hasBuildFile("foo.txt");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasBuildFile("_not_found_"));
    }

    @Test
    public void testHasBuildDirectory() {
        final File dir = new File(this.project.getLayout().getBuildDirectory().get().getAsFile(), "classes");
        assertThat(dir.mkdirs()).isTrue();
        assertThat(this.project).hasBuildDirectory("classes");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasBuildDirectory("_not_found_"));
    }

    @Test
    public void testHasPropertyName() {
        assertThat(this.project).doesNotHaveProperty("foo");
        this.project.getExtensions().getExtraProperties().set("foo", "hello");
        assertThat(this.project).hasProperty("foo");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasProperty("_not_found_"));
    }

    @Test
    public void testHasPropertyValue() {
        assertThat(this.project).doesNotHaveProperty("foo");
        this.project.getExtensions().getExtraProperties().set("foo", "hello");
        assertThat(this.project).doesNotHaveProperty("foo", "world");
        this.project.getExtensions().getExtraProperties().set("foo", "world");
        assertThat(this.project).hasProperty("foo", "world");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasProperty("foo", "joe"));
    }
}
