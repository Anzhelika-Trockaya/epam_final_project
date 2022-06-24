<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../header/header.jsp" %>

<fmt:message key="adding_medicine.title" var="adding_page_title"/>
<fmt:message key="edit_medicine.title" var="edit_page_title"/>
<fmt:message key="adding_medicine.name" var="label_name"/>
<fmt:message key="adding_medicine.international_name" var="label_international_name"/>
<fmt:message key="adding_medicine.form" var="label_form"/>
<fmt:message key="adding_medicine.dosage" var="label_dosage"/>
<fmt:message key="adding_medicine.need_prescription" var="label_need_prescription"/>
<fmt:message key="adding_medicine.manufacturer" var="label_manufacturer"/>
<fmt:message key="adding_medicine.number_in_package" var="label_number_in_package"/>
<fmt:message key="adding_medicine.total_packages" var="label_total_packages"/>
<fmt:message key="adding_medicine.price" var="label_price"/>
<fmt:message key="adding_medicine.ingredients" var="label_ingredients"/>
<fmt:message key="adding_medicine.instruction" var="label_instruction"/>
<fmt:message key="adding_medicine.image" var="label_image"/>
<fmt:message key="adding_medicine.successful_msg" var="successful_added_msg"/>
<fmt:message key="action.add" var="adding_btn_value"/>
<fmt:message key="action.edit" var="edit_btn_value"/>
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
<fmt:message key="edit_medicine.change_total_title" var="change_total_title"/>
<c:if test="${not empty medicine}">
    <c:set value="${medicine.id}" var="medicine_id"/>
    <c:set value="${medicine.name}" var="medicine_name"/>
    <c:set value="${medicine.internationalNameId}" var="medicine_international_name_id"/>
    <c:set value="${medicine.price}" var="medicine_price"/>
    <c:set value="${medicine.totalPackages}" var="medicine_total_packages"/>
    <c:set value="${medicine.numberInPackage}" var="medicine_number_in_package"/>
    <c:set value="${medicine.formId}" var="medicine_form_id"/>
    <c:set value="${medicine.dosage}" var="medicine_dosage"/>
    <c:set value="${medicine.dosageUnit}" var="medicine_dosage_unit"/>
    <c:set value="${medicine.ingredients}" var="medicine_ingredients"/>
    <c:set value="${medicine.needPrescription()}" var="medicine_need_prescription"/>
    <c:set value="${medicine.manufacturerId}" var="medicine_manufacturer_id"/>
    <c:set value="${medicine.instruction}" var="medicine_instruction"/>
    <c:set value="${medicine.imagePath}" var="medicine_image_link"/>
</c:if>
<c:if test="${empty medicine_change_total_value}">
    <c:set value="0" var="medicine_change_total_value"/>
</c:if>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${empty medicine_id}">
                ${adding_page_title}
            </c:when>
            <c:otherwise>
                ${edit_page_title}
            </c:otherwise>
        </c:choose>
    </title>
    <c:set var="current_page" value="jsp/pharmacist/adding_medicine.jsp" scope="session"/>
</head>
<body>
<br>
<div class="medicine_form">
    <form name="medicine_form" id="medicine_form" action="${context_path}/controller" method="post"
          enctype="multipart/form-data" onsubmit="return validate()">
        <c:if test="${temp_successful_added}">
            <p class="successful_msg">${successful_added_msg}</p>
            <br>
        </c:if>
        <label for="name">${label_name}</label>
        <input type="text" class="uppercase" id="name" name="name" value="${medicine_name}"/>
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
               <c:if test="${medicine_need_prescription eq 'true'}">checked</c:if> value="true"/>
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
        <label for="number_in_package">${label_number_in_package}</label>
        <input type="text" id="number_in_package" name="number_in_package" value="${medicine_number_in_package}"/>
        <p id="incorrect_number_in_package_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_number_in_package}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_number_in_package}"/></p>
        </c:if>
        <br/>
        <c:choose>
            <c:when test="${empty medicine_id}">
                <label for="total_packages">${label_total_packages}</label>
                <input type="text" id="total_packages" name="total_packages" value="${medicine_total_packages}"/>
                <p id="incorrect_total_packages_msg" class="incorrect_data_msg"></p>
                <c:if test="${not empty incorrect_total_packages}">
                    <p class="incorrect_data_msg"><fmt:message key="${incorrect_total_packages}"/></p>
                </c:if>
                <br/>
            </c:when>
            <c:otherwise>
                <p>${label_total_packages}: ${medicine_total_packages}
                    <label for="change_total_value">${change_total_title}</label>
                    <input type="text" id="change_total_value" name="change_total_value"
                           value="${medicine_change_total_value}" onclick="this.select()"
                           onkeyup="validateOnKeyUpChangeTotalValue()" onchange="validateOnChangeChangeTotalValue()"/>
                </p>
                <c:if test="${not empty incorrect_change_total_value}">
                    <p class="incorrect_data_msg"><fmt:message key="${incorrect_change_total_value}"/></p>
                </c:if>
            </c:otherwise>
        </c:choose>
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
        <input type="file" style="display:none;" onchange="onChangeFile()" id="image" name="image"
               accept=".jpg, .jpeg, .png, .bmp" value="${medicine_image_link}">
        <p id="file_path_msg" class="successful_msg">${medicine_image_link}</p>
        <div id="imagePreview" style="margin-top: 20px">
            <c:if test="${not empty medicine_image_link}">
                <img src="${context_path}/uploadImage?image_path=${medicine_image_link}" height="216" width="297"
                     alt="no image">
            </c:if>
        </div>
        <p id="incorrect_file_msg" class="incorrect_data_msg"></p>
        <c:if test="${not empty incorrect_file}">
            <p class="incorrect_data_msg"><fmt:message key="${incorrect_file}"/></p>
        </c:if>
        <br/>
        <input type="hidden" name="old_image_path" value="${medicine_image_link}">
        <c:choose>
            <c:when test="${not empty medicine_id}">
                <input type="hidden" name="command" value="edit_medicine"/>
                <input type="hidden" name="medicine_id" value="${medicine_id}"/>
                <input type="submit" name="edit_sub" value="${edit_btn_value}"/>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="command" value="add_medicine"/>
                <input type="submit" name="add_sub" value="${adding_btn_value}"/>
            </c:otherwise>
        </c:choose>
        <br/>
    </form>
</div>
</body>
<script>
    const fileInput = document.forms["medicine_form"]["image"];
    const allowedExtensionsPattern = /.+(\.jpg|\.jpeg|\.png|\.bmp|\.JPG|\.JPEG|\.PNG|\.BMP)$/;

    function onChangeFile() {
        document.forms["medicine_form"]["old_image_path"].value = "";
        if (validateFile()) {
            document.getElementById("file_path_msg").innerHTML = fileInput.value;
            makeInputCorrect("incorrect_file_msg");
            showPreview(fileInput);
        }
    }

    function validateFile() {
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
        const intPattern = /^[1-9]\d{0,8}$/;
        const pricePattern = /^\d{1,20}(.\d{2})?$/;
        const nameInput = document.forms["medicine_form"]["name"];
        const internationalNameInput = document.forms["medicine_form"]["international_name"];
        const formInput = document.forms["medicine_form"]["form"];
        const dosageInput = document.forms["medicine_form"]["dosage"];
        const unitInput = document.forms["medicine_form"]["dosage_unit"];
        const manufacturerInput = document.forms["medicine_form"]["manufacturer"];
        const numberInPackageInput = document.forms["medicine_form"]["number_in_package"];
        const priceInput = document.forms["medicine_form"]["price"];
        const ingredientsInput = document.forms["medicine_form"]["ingredients"];
        const instructionInput = document.forms["medicine_form"]["instruction"];
        let result = true;
        if (${empty medicine_id}) {
            const totalPackagesInput = document.forms["medicine_form"]["total_packages"];
            if (!(validateRequired(totalPackagesInput, "incorrect_total_packages_msg", "${incorrect_required_msg_text}") &&
                validatePatternMismatch(totalPackagesInput, intPattern, "incorrect_total_packages_msg", "${incorrect_integer_msg_text}"))) {
                result = false;
            }
        }
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
        if (!(validateRequired(numberInPackageInput, "incorrect_number_in_package_msg", "${incorrect_required_msg_text}") &&
            validatePatternMismatch(numberInPackageInput, intPattern, "incorrect_number_in_package_msg", "${incorrect_integer_msg_text}"))) {
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
        if (document.getElementById("file_path_msg").innerText === "") {
            makeFileInputIncorrect(fileInput, "${incorrect_required_msg_text}");
            result = false;
        }
        return result;
    }

    function validateOnKeyUpChangeTotalValue() {
        const numberPattern = /^-|(-?[1-9]\d{0,8})$/;
        var changeTotalValue = document.getElementById('change_total_value');
        if (!numberPattern.test(changeTotalValue.value)) {
            changeTotalValue.value = '0';
        } else {
            var value = parseInt(changeTotalValue.value);
            var totalValue = parseInt(${medicine_total_packages});
            var minValue = -totalValue;
            var maxValue = 999999999 - totalValue;
            if (value < minValue) {
                changeTotalValue.value = minValue;
            } else if (value > maxValue) {
                changeTotalValue.value = maxValue;
            }
        }
    }

    function validateOnChangeChangeTotalValue() {
        const numberPattern = /^-?[1-9]\d{0,8}$/;
        var changeTotalValue = document.getElementById('change_total_value');
        if (!numberPattern.test(changeTotalValue.value)) {
            changeTotalValue.value = '0';
        }
    }

    function validateFileRequired() {
        return document.getElementById("file_path_msg").innerText !== "";
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
