package com.lmax.intellijLint;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.junit.Assert;

import java.util.List;

public class OptionalNullInspectionTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/testdata/OptionalNullInspectionTest";
    }

    public void testOptionalNullAssignmentInMethod() throws Exception {
        doTest();
    }

    public void testOptionalNullAssignmentInFieldInitialization() throws Exception {
        doTest();
    }

    public void testOptionalNullAssignmentInVariableInitialization() throws Exception {
        doTest();
    }

    public void testOptionalLongNullAssignmentInMethod() throws Exception {
        doTest();
    }

    public void testOptionalLongNullAssignmentInFieldInitialization() throws Exception {
        doTest();
    }

    public void testOptionalLongNullAssignmentInVariableInitialization() throws Exception {
        doTest();
    }

    private void doTest() {
        doTest(getTestDirectoryName());
    }

    private void doTest(String filename) {
        doTest(filename, 1);
    }

    private void doTest(String filename, int inspectionCount) {
        myFixture.configureByFile(filename + ".java");
        myFixture.enableInspections(new OptionalNullInspection());

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();
        Assert.assertEquals(
                inspectionCount,
                highlightInfos.stream()
                        .filter(x -> "Assigning null to optional".equals(x.getDescription()))
                        .count());

        myFixture.getAllQuickFixes()
                .stream()
                .filter(x -> x.getText().equals("Replace null assignment with optional.empty()"))
                .forEach(intention -> myFixture.launchAction(intention));

        myFixture.checkResultByFile(filename + "Fixed.java");
    }
}