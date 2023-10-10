document.addEventListener("DOMContentLoaded", function () {
    const numIndependentVariablesInput = document.getElementById("numIndependentVariables");
    const numExperimentGroupsInput = document.getElementById("numExperimentGroups");
    const experimentsPerGroupInput = document.getElementById("experimentsPerGroup");
    const meanInput = document.getElementById("meanInput");
    const stdDevInput = document.getElementById("stdDevInput");
    const generateNormallyDistributedRandomNumbersButton = document.getElementById("generateNormallyDistributedRandomNumbersButton");

    numIndependentVariablesInput.addEventListener("input", showInputArrays);
    numExperimentGroupsInput.addEventListener("input", showInputArrays);
    experimentsPerGroupInput.addEventListener("input", showInputArrays);
    generateNormallyDistributedRandomNumbersButton.addEventListener("click", generateNormallyDistributedRandomNumbers);

    function showInputArrays() {
        switch (this.id) {
            case "numIndependentVariables":
                showCoefficients();
                showIndependentVariables();
            case "numExperimentGroups":
                showErrors()
            case "experimentsPerGroup":
                showErrors()
                showIndependentVariables();
        }
    }

    function showCoefficients() {
        const numIndependentVariables = parseInt(numIndependentVariablesInput.value);
        const coefficientsGroup = document.getElementById("coefficientsGroup");
        const coefficientsTable = document.getElementById("coefficientsTable");

        if (!isNaN(numIndependentVariables) && numIndependentVariables > 0) {
            coefficientsGroup.classList.remove("hidden");
            coefficientsTable.innerHTML = generateMatrixTable(1, numIndependentVariables + 1, 0, 'θ');
        } else {
            coefficientsGroup.classList.add("hidden");
            coefficientsTable.innerHTML = "";
        }
    }

    function showErrors() {
        const numExperimentGroups = parseInt(numExperimentGroupsInput.value);
        const experimentsPerGroup = parseInt(experimentsPerGroupInput.value);
        const errorsGroup = document.getElementById("errorsGroup");
        const errorsTable = document.getElementById("errorsTable");

        if (!isNaN(numExperimentGroups) && !isNaN(experimentsPerGroup) && numExperimentGroups > 0 && experimentsPerGroup > 0) {
            errorsGroup.classList.remove("hidden");
            errorsTable.innerHTML = generateMatrixTable(experimentsPerGroup, numExperimentGroups, 1, 'E');
        } else {
            errorsGroup.classList.add("hidden");
            errorsTable.innerHTML = "";
        }
    }

    function showIndependentVariables() {
        const experimentsPerGroup = parseInt(experimentsPerGroupInput.value);
        const numIndependentVariables = parseInt(numIndependentVariablesInput.value);
        const independentVariablesGroup = document.getElementById("independentVariablesGroup");
        const independentVariablesTable = document.getElementById("independentVariablesTable");

        if (!isNaN(experimentsPerGroup) && !isNaN(numIndependentVariables) && experimentsPerGroup > 0 && numIndependentVariables > 0) {
            independentVariablesGroup.classList.remove("hidden");
            independentVariablesTable.innerHTML = generateMatrixTable(experimentsPerGroup, numIndependentVariables, 1, 'X');
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

    function generateNormallyDistributedRandomNumbers() {
        const mean = parseFloat(meanInput.value);
        const stdDev = parseFloat(stdDevInput.value);

        if (isNaN(mean) || isNaN(stdDev)) {
            alert("Please enter valid mean and standard deviation values.");
            return;
        }

        const rows = parseInt(experimentsPerGroupInput.value);
        const columns = parseInt(numExperimentGroupsInput.value);

        const requestData = { mean, stdDev, rows, columns };

        fetch("/random-numbers/normal-distribution/matrix", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })
        .then(response => response.json())
        .then(data => {
            displayMatrixInTable(data, "errorsTable");
            console.log(data);
        })
        .catch(error => {
            console.error("Error generating random numbers:", error);
            alert("An error occurred while generating random numbers.");
        });
    }

    function displayMatrixInTable(matrix, tableId) {
        const table = document.getElementById(tableId);

        for (let i = 0; i < matrix.length; i++) {
            const rowData = matrix[i];
            const row = table.rows[i + 1];

            for (let j = 0; j < rowData.length; j++) {
                const cellData = rowData[j];
                const cell = row.cells[j].querySelector(".matrix-input");
                cell.value = cellData;
            }
        }
    }
});
