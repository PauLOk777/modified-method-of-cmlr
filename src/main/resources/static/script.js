document.addEventListener("DOMContentLoaded", function () {
    const numIndependentVariablesInput = document.getElementById("numIndependentVariables");
    const experimentsPerGroupInput = document.getElementById("experimentsPerGroup");
    const repetitionsNumberOfActiveExperimentsInput = document.getElementById("repetitionsNumberOfActiveExperiments");
    const numberOfValidationSequencesInput = document.getElementById("numberOfValidationSequences");
    const numberOfRunsInput = document.getElementById("numberOfRuns");
    const meanInputErrors = document.getElementById("meanInputErrors");
    const stdDevInputErrors = document.getElementById("stdDevInputErrors");
    const startRangeInputCoefficients = document.getElementById("startRangeInputCoefficients");
    const endRangeInputCoefficients = document.getElementById("endRangeInputCoefficients");
    const generateRandomNumbersCoefficientsButton = document.getElementById("generateRandomNumbersCoefficients");
    const startRangeInputIV = document.getElementById("startRangeInputIV");
    const endRangeInputIV = document.getElementById("endRangeInputIV");
    const generateRandomNumbersIVButton = document.getElementById("generateRandomNumbersIV");
    const startRangeInputErrors = document.getElementById("startRangeInputErrors");
    const endRangeInputErrors = document.getElementById("endRangeInputErrors");
    const integerCheckboxCoefficients = document.getElementById("integerCheckboxCoefficients");
    const integerCheckboxIV = document.getElementById("integerCheckboxIV");
    const generateResultsButton = document.getElementById("generateResultsButton");
    const fullScreenToggle = document.getElementById("fullScreenToggle");
    const percentageOfCorrectModelsSpan = document.getElementById("percentageOfCorrectModels");
    const percentageOfIncorrectModelsWithOneIncorrectZeroSpan = document.getElementById("percentageOfIncorrectModelsWithOneIncorrectZero");
    const percentageOfIncorrectModelsWithTwoPlusIncorrectZerosSpan = document.getElementById("percentageOfIncorrectModelsWithTwoPlusIncorrectZeros");
    const meanComparisonMeasureValueForCorrectModelsSpan = document.getElementById("meanComparisonMeasureValueForCorrectModels");
    const minComparisonMeasureValueForCorrectModelsSpan = document.getElementById("minComparisonMeasureValueForCorrectModels");
    const maxComparisonMeasureValueForCorrectModelsSpan = document.getElementById("maxComparisonMeasureValueForCorrectModels");
    const copyCoefficientsButton = document.getElementById("copyCoefficients");
    const copyIndependentVariablesButton = document.getElementById("copyIndependentVariables");
    const importJsonMatrixForCoefficientsButton = document.getElementById("importJsonMatrixForCoefficients");
    const importJsonMatrixForIndependentVariablesButton = document.getElementById("importJsonMatrixForIndependentVariables");
    const typeOfErrorsGenerationSwitch = document.getElementById("typeOfErrorsGenerationSwitch");
    const normallyDistributedRandomNumbersInputs = document.getElementById("normallyDistributedRandomNumbersInputs");
    const randomNumbersInputs = document.getElementById("randomNumbersInputs");

    numIndependentVariablesInput.addEventListener("input", showInputArrays);
    experimentsPerGroupInput.addEventListener("input", showInputArrays);
    repetitionsNumberOfActiveExperimentsInput.addEventListener("input", showInputArrays);
    numberOfValidationSequencesInput.addEventListener("input", showInputArrays);
    generateRandomNumbersCoefficientsButton.addEventListener("click", generateRandomNumbersInCoefficientsRange);
    generateRandomNumbersIVButton.addEventListener("click", generateRandomNumbersInIndependentVariablesRange);
    generateResultsButton.addEventListener("click", generateResults);
    copyCoefficientsButton.addEventListener("click", copyTableToClipboardInJson);
    copyIndependentVariablesButton.addEventListener("click", copyTableToClipboardInJson);
    importJsonMatrixForCoefficientsButton.addEventListener("click", importJsonMatrix);
    importJsonMatrixForIndependentVariablesButton.addEventListener("click", importJsonMatrix);
    typeOfErrorsGenerationSwitch.addEventListener("change", switchTypeOfErrorsGeneration);
    fullScreenToggle.addEventListener("change", toggleFullScreen);

    function showInputArrays() {
        switch (this.id) {
            case "numIndependentVariables":
                showCoefficients();
                showIndependentVariables();
            case "experimentsPerGroup":
                showErrorsControlBlock()
                showIndependentVariables();
            case "repetitionsNumberOfActiveExperiments":
                showErrorsControlBlock();
            case "numberOfValidationSequences":
                showErrorsControlBlock();
        }
    }

    function showCoefficients() {
        const numIndependentVariables = parseInt(numIndependentVariablesInput.value);
        const coefficientsGroup = document.getElementById("coefficientsGroup");
        const coefficientsTable = document.getElementById("coefficientsTable");

        if (!isNaN(numIndependentVariables) && numIndependentVariables > 0) {
            coefficientsTable.innerHTML = generateMatrixTable(1, numIndependentVariables + 1, 0, 'θ');
            importJsonMatrixForCoefficientsButton.dataset.rows = 1;
            importJsonMatrixForCoefficientsButton.dataset.columns = numIndependentVariables + 1;
            coefficientsGroup.classList.remove("hidden");
        } else {
            coefficientsTable.innerHTML = "";
            importJsonMatrixForCoefficientsButton.dataset.rows = "";
            importJsonMatrixForCoefficientsButton.dataset.columns = "";
            coefficientsGroup.classList.add("hidden");
        }
    }

    function showErrorsControlBlock() {
        const repetitionsNumberOfActiveExperiments = parseInt(repetitionsNumberOfActiveExperimentsInput.value);
        const numberOfValidationSequences = parseInt(numberOfValidationSequencesInput.value);
        const experimentsPerGroup = parseInt(experimentsPerGroupInput.value);
        const errorsGroup = document.getElementById("errorsGroup");
        const errorsTable = document.getElementById("errorsTable");

        if (!isNaN(repetitionsNumberOfActiveExperiments) && !isNaN(numberOfValidationSequences) &&
             !isNaN(experimentsPerGroup) && repetitionsNumberOfActiveExperiments > 0 &&
             numberOfValidationSequences > 0 && experimentsPerGroup > 0) {
            errorsGroup.classList.remove("hidden");
        } else {
            errorsGroup.classList.add("hidden");
        }
    }

    function showIndependentVariables() {
        const experimentsPerGroup = parseInt(experimentsPerGroupInput.value);
        const numIndependentVariables = parseInt(numIndependentVariablesInput.value);
        const independentVariablesGroup = document.getElementById("independentVariablesGroup");
        const independentVariablesTable = document.getElementById("independentVariablesTable");

        if (!isNaN(experimentsPerGroup) && !isNaN(numIndependentVariables) && experimentsPerGroup > 0 && numIndependentVariables > 0) {
            independentVariablesTable.innerHTML = generateMatrixTable(experimentsPerGroup, numIndependentVariables, 1, 'X');
            importJsonMatrixForIndependentVariablesButton.dataset.rows = experimentsPerGroup;
            importJsonMatrixForIndependentVariablesButton.dataset.columns = numIndependentVariables;
            independentVariablesGroup.classList.remove("hidden");
        } else {
            independentVariablesTable.innerHTML = "";
            importJsonMatrixForIndependentVariablesButton.dataset.rows = "";
            importJsonMatrixForIndependentVariablesButton.dataset.columns = "";
            independentVariablesGroup.classList.add("hidden");
        }
    }

    function generateMatrixTable(rows, cols, startIndex, header, readOnly) {
        let matrixTableHTML = "<thead><tr>";

        for (let i = 1; i <= cols; i++) {
            matrixTableHTML += `<th>${header + startIndex++}</th>`;
        }

        matrixTableHTML += "</tr></thead><tbody>";

        for (let i = 0; i < rows; i++) {
            matrixTableHTML += "<tr>";
            for (let j = 0; j < cols; j++) {
                if (readOnly) {
                    matrixTableHTML += `<td><input type="text" class="matrix-output" readonly /></td>`;
                } else {
                    matrixTableHTML += `<td><input type="text" class="matrix-input" /></td>`;
                }
            }
            matrixTableHTML += "</tr>";
        }

        matrixTableHTML += "</tbody>";

        return matrixTableHTML;
    }

    function importJsonMatrix() {
        const text = document.getElementById(this.dataset.textareaId).value;
        try {
            const matrix = JSON.parse(text);

            if (!Array.isArray(matrix)) {
                alert('Invalid matrix format: Must be an array of arrays.');
                return;
            }

            const isValidMatrix = matrix.every(row => Array.isArray(row));
            if (!isValidMatrix) {
                alert('Invalid matrix format: Each row must be an array.');
                return;
            }

            const isNumericMatrix = matrix.every(row => row.every(value => !isNaN(value)));
            if (!isNumericMatrix) {
                alert('Invalid matrix content: All values must be numbers.');
                return;
            }

            if (matrix.length != this.dataset.rows) {
                alert(`Invalid matrix format: matrix should have ${this.dataset.rows} rows.`);
                return;
            }

            const isValidColumns = matrix.every(row => row.length == this.dataset.columns);
            if (!isValidColumns) {
                alert(`Invalid matrix format: matrix should have ${this.dataset.columns} columns.`);
                return;
            }

            displayMatrixInTable(matrix, this.dataset.tableId);
            alert("Matrix has been successfully imported");
        } catch (error) {
            alert('Please provide a valid JSON.');
        }
    }

    function displayMatrixInTable(matrix, tableId) {
        const table = document.getElementById(tableId);

        for (let i = 0; i < matrix.length; i++) {
            const rowData = matrix[i];
            const row = table.rows[i + 1];

            for (let j = 0; j < rowData.length; j++) {
                const cellData = rowData[j];
                const cell = row.cells[j].querySelector("input");
                cell.value = cellData;
            }
        }
    }

    function generateRandomNumbersInCoefficientsRange() {
        const start = parseFloat(startRangeInputCoefficients.value);
        const end = parseFloat(endRangeInputCoefficients.value);

        if (isNaN(start) || isNaN(end)) {
            alert("Please enter valid start and end range values.");
            return;
        }

        const rows = 1;
        const columns = parseInt(numIndependentVariablesInput.value) + 1;
        const dataType = integerCheckboxCoefficients.checked ? 'int' : 'double'

        generateRandomNumbers(start, end, rows, columns, dataType, "coefficientsTable");
    }

    function generateRandomNumbersInIndependentVariablesRange() {
        const start = parseFloat(startRangeInputIV.value);
        const end = parseFloat(endRangeInputIV.value);

        if (isNaN(start) || isNaN(end)) {
            alert("Please enter valid start and end range values.");
            return;
        }

        const rows = parseInt(experimentsPerGroupInput.value);
        const columns = parseInt(numIndependentVariablesInput.value);
        const dataType = integerCheckboxIV.checked ? 'int' : 'double'

        generateRandomNumbers(start, end, rows, columns, dataType, "independentVariablesTable");
    }

    function generateRandomNumbers(start, end, rows, columns, dataType, tableId) {
        const requestData = {
            range: { start, end },
            rows,
            columns
        };
        fetch(`/random-numbers/uniformly-distribution/${dataType}/matrix`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })
        .then(response => {
            if (response.status === 200 || response.status === 400) {
                return response.json();
            } else {
                throw new Error('Something went wrong on server side');
            }
        })
        .then(data => {
            if (Array.isArray(data)) {
                displayMatrixInTable(data, tableId);
            } else {
                handleBadRequestErrors(data);
            }
        })
        .catch(error => {
            console.error("Error generating random numbers:", error);
            alert("An error occurred while generating random numbers.");
        });
    }

    function switchTypeOfErrorsGeneration() {
        if (typeOfErrorsGenerationSwitch.checked) {
            normallyDistributedRandomNumbersInputs.classList.remove("hidden");
            randomNumbersInputs.classList.add("hidden");
        } else {
            normallyDistributedRandomNumbersInputs.classList.add("hidden");
            randomNumbersInputs.classList.remove("hidden");
        }
    }

    function generateResults() {
        const repetitionsNumberOfActiveExperiments = parseInt(repetitionsNumberOfActiveExperimentsInput.value);
        const numberOfValidationSequences = parseInt(numberOfValidationSequencesInput.value);
        const numberOfRuns = parseInt(numberOfRunsInput.value);

        if (isNaN(repetitionsNumberOfActiveExperiments) || isNaN(numberOfValidationSequences) ||
                numberOfRuns < 1 || !validateTablesInputs()) {
            alert('Please check for correctness all fields.');
            return;
        }

        const independentVariables = getDataFromTable("independentVariablesTable");
        const correctCoefficients = getDataFromTable("coefficientsTable")[0];
        const pathSuffix = typeOfErrorsGenerationSwitch.checked
                        ? "/normally-distributed-random-numbers"
                        : "/uniformly-distributed-random-numbers";

        const requestData = {
            repetitionsNumberOfActiveExperiments,
            numberOfValidationSequences,
            numberOfRuns,
            correctCoefficients,
            independentVariables
        };

        if (typeOfErrorsGenerationSwitch.checked) {
            const mean = parseFloat(meanInputErrors.value);
            const stdDev = parseFloat(stdDevInputErrors.value);
            if (isNaN(mean) || isNaN(stdDev)) {
                alert('Please check for correctness parameters for normal distribution.');
                return;
            }

            requestData.mean = parseFloat(meanInputErrors.value);
            requestData.stdDev = parseFloat(stdDevInputErrors.value);
        } else {
            const start = parseFloat(startRangeInputErrors.value);
            const end = parseFloat(endRangeInputErrors.value);
            if (isNaN(start) || isNaN(end)) {
                alert('Please check for correctness parameters for uniformly distribution.');
                return;
            }

            requestData.range = {};
            requestData.range.start = parseFloat(startRangeInputErrors.value);
            requestData.range.end = parseFloat(endRangeInputErrors.value);
        }


        fetch("/multivariate-linear-regression/modified-method-of-cmlr" + pathSuffix, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })
        .then(response => {
            if (response.status === 200 || response.status === 400) {
                return response.json();
            } else {
                throw new Error('Something went wrong on server side');
            }
        })
        .then(data => {
            if (!isNaN(data.percentageOfCorrectModels)) {
                percentageOfCorrectModelsSpan.innerHTML = data.percentageOfCorrectModels;
                percentageOfIncorrectModelsWithOneIncorrectZeroSpan.innerHTML = data.percentageOfIncorrectModelsWithOneIncorrectZero;
                percentageOfIncorrectModelsWithTwoPlusIncorrectZerosSpan.innerHTML = data.percentageOfIncorrectModelsWithTwoPlusIncorrectZeros;
                meanComparisonMeasureValueForCorrectModelsSpan.innerHTML = data.meanComparisonMeasureValueForCorrectModels
                if (data.minComparisonMeasureValue == -1) {
                    minComparisonMeasureValueForCorrectModelsSpan.innerHTML = "no correct models";
                    maxComparisonMeasureValueForCorrectModelsSpan.innerHTML = "no correct models";
                } else {
                    minComparisonMeasureValueForCorrectModelsSpan.innerHTML = data.minComparisonMeasureValueForCorrectModels;
                    maxComparisonMeasureValueForCorrectModelsSpan.innerHTML = data.maxComparisonMeasureValueForCorrectModels;
                }

                document.getElementById("resultsGroup").classList.remove("hidden");
                window.scrollTo(0, document.body.scrollHeight);
            } else {
                handleBadRequestErrors(data);
            }
        })
        .catch(error => {
            console.error(error);
        });
    }

    function handleBadRequestErrors(data) {
        if (data.globalErrors && data.globalErrors.length) {
            alert('Global Errors:\n' + data.globalErrors.join('\n'));
        }

        if (data.fieldErrors && data.fieldErrors.length) {
            alert('Field Errors:\n' + data.fieldErrors.join('\n'));
        }
    }

    function getDataFromTable(tableId) {
        const table = document.getElementById(tableId);
        const data = [];

        for (let i = 1; i < table.rows.length; i++) {
            const rowData = [];
            const row = table.rows[i];

            for (let j = 0; j < row.cells.length; j++) {
                rowData.push(parseFloat(row.cells[j].querySelector("input").value));
            }

            data.push(rowData);
        }

        return data;
    }

    function validateTablesInputs() {
        const inputs = document.querySelectorAll('input.matrix-input');
        for (const input of inputs) {
            const value = input.value.trim();
            if (value === '') {
                return false;
            }

            if (isNaN(value)) {
                return false;
            }
        }
        return true;
    }

    function copyTableToClipboardInJson() {
        const table = document.getElementById(this.dataset.tableId);
        const rows = table.getElementsByTagName('tr');
        const result = [];

        for (let i = 1; i < rows.length; i++) {
          const cells = rows[i].getElementsByTagName('td');
          const rowData = Array.from(cells).map(cell => cell.querySelector('input').value);
          result.push(`[${rowData.join(', ')}]`);
        }

        navigator.clipboard.writeText(`[${result.join(', ')}]`)
            .then(() => alert('Copied to clipboard!'));
    }

    function toggleFullScreen() {
        if (this.checked) {
            document.querySelector(".container").style.maxWidth = screen.width;
        } else {
            document.querySelector(".container").style.maxWidth = "800px";
        }
    }
});

function validateNumberInput(field, event) {
    if (event.code === "Backspace" || event.code === "Tab") {
        return;
    }

    const min = field.getAttribute("min") ? +field.getAttribute("min") : Number.MIN_VALUE;
    const max = field.getAttribute("max") ? +field.getAttribute("max") : Number.MAX_VALUE;
    const value = field.value + event.key;

    if (isNaN(value) || value < min || value > max) {
        event.preventDefault();
    }
}
