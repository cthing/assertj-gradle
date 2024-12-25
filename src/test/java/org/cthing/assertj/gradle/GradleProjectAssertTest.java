/*
 * Copyright 2024 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.cthing.assertj.gradle.GradleProjectAssert.assertThat;


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

    @BeforeEach
    public void setup() {
        this.project = ProjectBuilder.builder().build();
        this.project.getPluginManager().apply("java");
        this.project.getPluginManager().apply("checkstyle");
    }

    @Test
    public void testCreation() {
        assertThat(this.project).isNotNull();
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
    public void testHasExtensionNameWithType() {
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
    public void testHasConfiguration() {
        assertThat(this.project).doesNotHaveConfiguration("testConfig");
        this.project.getConfigurations().create("testConfig");
        assertThat(this.project).hasConfiguration("testConfig");
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.project)
                .hasConfiguration("_not_found_"));
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
