document.addEventListener("DOMContentLoaded", function () {
    const numVariablesInput = document.getElementById("numVariables");
    const numGroupsInput = document.getElementById("numGroups");
    const experimentsPerGroupInput = document.getElementById("experimentsPerGroup");

    numVariablesInput.addEventListener("input", showInputArrays);
    numGroupsInput.addEventListener("input", showInputArrays);
    experimentsPerGroupInput.addEventListener("input", showInputArrays);

    function showInputArrays() {
        switch (this.id) {
            case "numVariables":
                showCoefficients();
                showIndependentVariables();
            case "numGroups":
                showErrors()
            case "experimentsPerGroup":
                showErrors()
                showIndependentVariables();
        }
    }

    function showCoefficients() {
        const numVariables = parseInt(numVariablesInput.value);
        const coefficientsGroup = document.getElementById("coefficientsGroup");
        const coefficientsTable = document.getElementById("coefficientsTable");

        if (!isNaN(numVariables) && numVariables > 0) {
            coefficientsGroup.classList.remove("hidden");
            coefficientsTable.innerHTML = generateMatrixTable(1, numVariables + 1, 0, 'θ');
        } else {
            coefficientsGroup.classList.add("hidden");
            coefficientsTable.innerHTML = "";
        }
    }

    function showErrors() {
        const numGroups = parseInt(numGroupsInput.value);
        const experimentsPerGroup = parseInt(experimentsPerGroupInput.value);
        const errorsGroup = document.getElementById("errorsGroup");
        const errorsTable = document.getElementById("errorsTable");

        if (!isNaN(numGroups) && !isNaN(experimentsPerGroup) && numGroups > 0 && experimentsPerGroup > 0) {
            errorsGroup.classList.remove("hidden");
            errorsTable.innerHTML = generateMatrixTable(experimentsPerGroup, numGroups, 1, 'E');
        } else {
            errorsGroup.classList.add("hidden");
            errorsTable.innerHTML = "";
        }
    }

    function showIndependentVariables() {
        const experimentsPerGroup = parseInt(experimentsPerGroupInput.value);
        const numVariables = parseInt(numVariablesInput.value);
        const independentVariablesGroup = document.getElementById("independentVariablesGroup");
        const independentVariablesTable = document.getElementById("independentVariablesTable");

        if (!isNaN(experimentsPerGroup) && !isNaN(numVariables) && experimentsPerGroup > 0 && numVariables > 0) {
            independentVariablesGroup.classList.remove("hidden");
            independentVariablesTable.innerHTML = generateMatrixTable(experimentsPerGroup, numVariables, 1, 'X');
        } else {
            independentVariablesGroup.classList.add("hidden");
            independentVariablesTable.innerHTML = "";
        }
    }

    function generateMatrixTable(rows, cols, startIndex, header) {
        let matrixTableHTML = "<thead><tr>";

        for (let i = 1; i <= cols; i++) {
            matrixTableHTML += `<th>${header + startIndex++}</th>`;
        }

        matrixTableHTML += "</tr></thead><tbody>";

        for (let i = 0; i < rows; i++) {
            matrixTableHTML += "<tr>";
            for (let j = 0; j < cols; j++) {
                matrixTableHTML += `<td><input type="text" class="matrix-input" /></td>`;
            }
            matrixTableHTML += "</tr>";
        }

        matrixTableHTML += "</tbody>";

        return matrixTableHTML;
    }
});
