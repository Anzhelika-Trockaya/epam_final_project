<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="adding_medicine.title" var="adding_page_title"/>
<fmt:message key="adding_medicine.name" var="label_name"/>
<fmt:message key="adding_medicine.international_name" var="label_international_name"/>
<fmt:message key="adding_medicine.form" var="label_form"/>
<fmt:message key="adding_medicine.dosage" var="label_dosage"/>
<fmt:message key="adding_medicine.need_prescription" var="label_need_prescription"/>
<fmt:message key="adding_medicine.manufacturer" var="label_manufacturer"/>
<fmt:message key="adding_medicine.amount_in_part" var="label_amount_in_part"/>
<fmt:message key="adding_medicine.parts_amount_in_package" var="label_parts_in_package"/>
<fmt:message key="adding_medicine.total_number_of_parts" var="label_total_parts"/>
<fmt:message key="adding_medicine.price" var="label_price"/>
<fmt:message key="adding_medicine.ingredients" var="label_ingredients"/>
<fmt:message key="adding_medicine.instruction" var="label_instruction"/>
<fmt:message key="adding_medicine.image" var="label_image"/>
<fmt:message key="adding_medicine.successful_msg" var="successful_added_msg"/>
<fmt:message key="adding_medicine.add" var="adding_btn_value"/>
<fmt:message key="medicines.milliliter" var="unit_name_ml"/>
<fmt:message key="medicines.milligram" var="unit_name_mg"/>
<fmt:message key="medicines.gram" var="unit_name_g"/>
<fmt:message key="medicines.microgram" var="unit_name_mcg"/>
<fmt:message key="medicines.me" var="unit_name_me"/>
<fmt:message key="medicines.nanogram" var="unit_name_ng"/>
<fmt:message key="adding_medicine.incorrect_required" var="incorrect_required_msg_text"/>
<fmt:message key="adding_medicine.incorrect_not_integer" var="incorrect_integer_msg_text"/>
<fmt:message key="adding_medicine.incorrect_price" var="incorrect_price_msg_text"/>
<fmt:message key="adding_medicine.incorrect_file_size" var="incorrect_file_size_msg_text"/>
<fmt:message key="adding_medicine.incorrect_file_type" var="incorrect_file_type_msg_text"/>
<fmt:message key="adding_medicine.choose_file" var="choose_file"/>
<html>
<head>
    <title>${adding_page_title}</title>
    <c:set var="current_page" value="jsp/pharmacist/adding_medicine.jsp" scope="session"/>
</head>
<body>
<div class="medicine_form">
    <form name="medicine_form" id="medicine_form" action="${context_path}/controller" method="post"
          enctype="multipart/form-data" onsubmit="return validate()">
        <input type="hidden" name="command" value="add_medicine"/>
        <c:if test="${not empty successful_added}">
            <p class="successful_msg">${successful_added_msg}</p>
            <br/>
        </c:if>
        <label for="name">${label_name}</label>
        <input type="text" id="name" name="name" value="${medicine_name}"/>
        <p id="incorrect_name_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_name}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_name}"/></p>
        </c:if>
        <br/>
        <label for="international_name">${label_international_name}</label>
        <select id="international_name" name="international_name" size="1">
            <option id="default_international_name" selected value="">-</option>
            <c:forEach var="international_name" items="${international_names_list}">
                <option
                        <c:if test="${medicine_international_name_id eq international_name.id}">selected</c:if>
                        value="${international_name.id}">
                        ${international_name.internationalName}
                </option>
            </c:forEach>
        </select>
        <p id="incorrect_international_name_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_international_name}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_international_name}"/></p>
        </c:if>
        <br/>
        <label for="need_prescription">${label_need_prescription}</label>
        <input type="checkbox" id="need_prescription" name="need_prescription"
               <c:if test="${medicine_need_prescription}">checked</c:if> value="true"/>
        <br/>
        <br/>
        <label for="form">${label_form}</label>
        <select id="form" name="form" size="1">
            <option id="default_form" selected value="">-</option>
            <c:forEach var="form" items="${forms_list}">
                <option
                        <c:if test="${medicine_form_id eq form.id}">selected</c:if> value="${form.id}">
                        ${form.name}
                </option>
            </c:forEach>
        </select>
        <p id="incorrect_form_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_form}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_form}"/></p>
        </c:if>
        <br/>
        <label for="dosage">${label_dosage}</label>
        <input type="text" id="dosage" name="dosage" value="${medicine_dosage}"/>
        <select id="dosage_unit" name="dosage_unit" size="1">
            <option id="default_dosage_unit" selected value="">-</option>
            <option
                    <c:if test="${medicine_dosage_unit eq 'MILLILITER'}">selected</c:if> value="MILLILITER">
                ${unit_name_ml}
            </option>
            <option
                    <c:if test="${medicine_dosage_unit eq 'MILLIGRAM'}">selected</c:if> value="MILLIGRAM">
                ${unit_name_mg}
            </option>
            <option
                    <c:if test="${medicine_dosage_unit eq 'GRAM'}">selected</c:if> value="GRAM">
                ${unit_name_g}
            </option>
            <option
                    <c:if test="${medicine_dosage_unit eq 'MICROGRAM'}">selected</c:if> value="MICROGRAM">
                ${unit_name_mcg}
            </option>
            <option
                    <c:if test="${medicine_dosage_unit eq 'ME'}">selected</c:if> value="ME">
                ${unit_name_me}
            </option>
            <option
                    <c:if test="${medicine_dosage_unit eq 'NANOGRAM'}">selected</c:if> value="NANOGRAM">
                ${unit_name_ng}
            </option>
        </select>
        <p id="incorrect_dosage_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_dosage}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_dosage}"/></p>
        </c:if>
        <c:if test="${not empty incorrect_dosage_unit}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_dosage_unit}"/></p>
        </c:if>
        <br/>
        <label for="manufacturer">${label_manufacturer}</label>
        <select id="manufacturer" name="manufacturer" size="1">
            <option id="default_manufacturer" selected value="">-</option>
            <c:forEach var="manufacturer" items="${manufacturers_list}">
                <option
                        <c:if test="${medicine_manufacturer_id eq manufacturer.id}">selected</c:if>
                        value="${manufacturer.id}">${manufacturer.name} (${manufacturer.country})
                </option>
            </c:forEach>
        </select>
        <p id="incorrect_manufacturer_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_manufacturer}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_manufacturer}"/></p>
        </c:if>
        <br/>
        <label for="parts_in_package">${label_parts_in_package}</label>
        <input type="text" id="parts_in_package" name="parts_in_package" value="${medicine_parts_in_package}"/>
        <p id="incorrect_parts_in_package_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_parts_in_package}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_parts_in_package}"/></p>
        </c:if>
        <br/>
        <label for="amount_in_part">${label_amount_in_part}</label>
        <input type="text" id="amount_in_part" name="amount_in_part" value="${medicine_amount_in_part}"/>
        <p id="incorrect_amount_in_part_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_amount_in_part}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_amount_in_part}"/></p>
        </c:if>
        <br/>
        <label for="total_parts">${label_total_parts}</label>
        <input type="text" id="total_parts" name="total_parts" value="${medicine_total_parts}"/>
        <p id="incorrect_total_parts_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_total_parts}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_total_parts}"/></p>
        </c:if>
        <br/>
        <label for="price">${label_price}</label>
        <input type="text" id="price" name="price" value="${medicine_price}"/>
        <p id="incorrect_price_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_price}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_price}"/></p>
        </c:if>
        <br/>
        <label for="ingredients">${label_ingredients}</label>
        <textarea id="ingredients" name="ingredients" maxlength="100">${medicine_ingredients}</textarea>
        <p id="incorrect_ingredients_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_ingredients}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_ingredients}"/></p>
        </c:if>
        <br/>
        <label for="instruction">${label_instruction}</label>
        <textarea id="instruction" name="instruction" maxlength="100">${medicine_instruction}</textarea>
        <p id="incorrect_instruction_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_instruction}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_instruction}"/></p>
        </c:if>
        <br/>
        <input type="button" id="loadFileXml" value="${choose_file}"
               onclick="document.getElementById('image').click();"/>
        <input type="file" style="display:none;" onchange="checkFile()" id="image" name="image">
        <p id="file_path_msg" class="successful_msg"></p>
        <div id="imagePreview" style="margin-top: 20px"></div>
        <p id="incorrect_file_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_file}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_file}"/></p>
        </c:if>
        <br/>
        <input type="submit" name="sub" value="${adding_btn_value}"/>
        <br/>
    </form>
</div>
</body>
<script>
    function checkFile() {
        const fileInput = document.forms["medicine_form"]["image"];
        if (validateFile()) {
            document.getElementById("file_path_msg").innerHTML = fileInput.value;
            makeInputCorrect("incorrect_file_msg");
            showPreview(fileInput);
        }
    }
    function validateFile() {
        const fileInput = document.forms["medicine_form"]["image"];
        const allowedExtensionsPattern = /.+(\.jpg|\.jpeg|\.png|\.bmp|\.JPG|\.JPEG|\.PNG|\.BMP)$/;
        const filePath = fileInput.value;
        if (filePath === "") {
            makeFileInputIncorrect(fileInput, "${incorrect_required_msg_text}");
            return false;
        }
        if (!allowedExtensionsPattern.exec(filePath)) {
            makeFileInputIncorrect(fileInput, "${incorrect_file_type_msg_text}");
            return false;
        }
        const fileSize = fileInput.files.item(0).size;
        if (fileSize > (1024 * 1024)) {
            makeFileInputIncorrect(fileInput, "${incorrect_file_size_msg_text}");
            return false;
        }
        return true;
    }
    function makeFileInputIncorrect(fileInput, msg) {
        document.getElementById("imagePreview").innerHTML = "";
        document.getElementById("file_path_msg").innerHTML = "";
        makeInputIncorrect("incorrect_file_msg", msg);
        fileInput.value = '';
    }
    function showPreview(fileInput) {
        if (fileInput.files && fileInput.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById("imagePreview").innerHTML =
                    "<img src=\"" + e.target.result + "\" width=\"300\" alt=\"Preview image\"/>";
            };
            reader.readAsDataURL(fileInput.files[0]);
        }
    }
    function validate() {
        const intPattern = /^[1-9]\d{0,9}$/;
        const pricePattern = /^\d{1,20}(.\d{2})?$/;
        const nameInput = document.forms["medicine_form"]["name"];
        const internationalNameInput = document.forms["medicine_form"]["international_name"];
        const formInput = document.forms["medicine_form"]["form"];
        const dosageInput = document.forms["medicine_form"]["dosage"];
        const unitInput = document.forms["medicine_form"]["dosage_unit"];
        const manufacturerInput = document.forms["medicine_form"]["manufacturer"];
        const partsInPackageInput = document.forms["medicine_form"]["parts_in_package"];
        const amountInPartInput = document.forms["medicine_form"]["amount_in_part"];
        const totalPartsInput = document.forms["medicine_form"]["total_parts"];
        const priceInput = document.forms["medicine_form"]["price"];
        const ingredientsInput = document.forms["medicine_form"]["ingredients"];
        const instructionInput = document.forms["medicine_form"]["instruction"];
        let result = true;
        if (!validateRequired(nameInput, "incorrect_name_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!validateRequired(internationalNameInput, "incorrect_international_name_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!validateRequired(formInput, "incorrect_form_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!(validateRequired(dosageInput, "incorrect_dosage_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(dosageInput, intPattern, "incorrect_dosage_msg", "${incorrect_integer_msg_text}") &&
            validateRequired(unitInput, "incorrect_dosage_msg", "${incorrect_required_msg_text}"))) {
            result = false;
        }
        if (!validateRequired(manufacturerInput, "incorrect_manufacturer_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!(validateRequired(partsInPackageInput, "incorrect_parts_in_package_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(partsInPackageInput, intPattern, "incorrect_parts_in_package_msg", "${incorrect_integer_msg_text}"))) {
            result = false;
        }
        if (!(validateRequired(amountInPartInput, "incorrect_amount_in_part_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(amountInPartInput, intPattern, "incorrect_amount_in_part_msg", "${incorrect_integer_msg_text}"))) {
            result = false;
        }
        if (!(validateRequired(totalPartsInput, "incorrect_total_parts_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(totalPartsInput, intPattern, "incorrect_total_parts_msg", "${incorrect_integer_msg_text}"))) {
            result = false;
        }
        if (!(validateRequired(priceInput, "incorrect_price_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(priceInput, pricePattern, "incorrect_price_msg", "${incorrect_price_msg_text}"))) {
            result = false;
        }
        if (!validateRequired(ingredientsInput, "incorrect_ingredients_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!validateRequired(instructionInput, "incorrect_instruction_msg", "${incorrect_required_msg_text}")) {
            result = false;
        }
        if (!validateFile()) {
            result = false;
        }
        return result;
    }
    function validateRequired(input, msgPlaceId, msg) {
        const value = input.value;
        if (value === "") {
            makeInputIncorrect(msgPlaceId, msg);
            return false;
        }
        makeInputCorrect(msgPlaceId);
        return true;
    }
    function validatePatternMismatch(input, pattern, msgPlaceId, msg) {
        const value = input.value;
        if (!pattern.test(value)) {
            makeInputIncorrect(msgPlaceId, msg);
            return false;
        }
        makeInputCorrect(msgPlaceId);
        return true;
    }
    function makeInputIncorrect(msgPlaceId, msg) {
        document.getElementById(msgPlaceId).innerHTML = msg;
    }
    function makeInputCorrect(msgPlaceId) {
        document.getElementById(msgPlaceId).innerHTML = "";
    }
</script>
</html>
