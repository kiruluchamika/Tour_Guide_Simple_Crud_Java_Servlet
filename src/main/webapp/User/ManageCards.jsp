<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../User/partials/UserHeader.jsp" %>

<main class="p-6">

    <!-- Success/Error Messages -->
    <c:if test="${not empty success}">
        <div class="bg-green-100 border-l-4 border-green-500 text-green-700 p-4 mb-6 animate-bounce">
            <p>${success}</p>
            <c:remove var="success" scope="session"/>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6 animate-pulse">
            <p>${error}</p>
            <c:remove var="error" scope="session"/>
        </div>
    </c:if>

    <!-- Header -->
    <div class="flex justify-between items-center mb-6">
        <h2 class="text-3xl font-bold text-white">My Payment Cards</h2>
        <a href="${pageContext.request.contextPath}/cards?mode=add" 
           class="bg-white hover:bg-purple-100 text-purple-700 px-5 py-2 rounded-full text-sm font-semibold shadow transition">
            <i class="fas fa-plus mr-2"></i>Add New Card
        </a>
    </div>

    <!-- Add / Edit Card Form -->
    <c:if test="${mode == 'add' || mode == 'edit'}">
        <div class="bg-white rounded-2xl shadow-lg p-6 mb-8 max-w-xl mx-auto">
            <h3 class="text-xl font-semibold text-purple-700 mb-4">
                <c:choose>
                    <c:when test="${mode == 'add'}">Add Payment Card</c:when>
                    <c:otherwise>Edit Payment Card</c:otherwise>
                </c:choose>
            </h3>

            <form action="${pageContext.request.contextPath}/cards" method="POST" class="space-y-4">
                <input type="hidden" name="action" value="${mode == 'add' ? 'add' : 'update'}">
                <c:if test="${mode == 'edit'}">
                    <input type="hidden" name="cardId" value="${currentCard.cardId}">
                </c:if>

                <div>
                    <label class="block text-gray-700 mb-1">Cardholder Name</label>
                    <input type="text" name="cardholderName" value="${currentCard.cardholderName}" required class="w-full border rounded px-3 py-2">
                </div>

                <div>
                    <label class="block text-gray-700 mb-1">Card Number (Last 4 digits only for edit)</label>
                    <input type="text" name="cardNumber" maxlength="19"
                           value="${mode == 'edit' ? 'XXXX XXXX XXXX ' += currentCard.cardNumberLast4 : ''}" 
                           placeholder="XXXX XXXX XXXX XXXX" required class="w-full border rounded px-3 py-2" ${mode == 'edit' ? 'readonly' : ''}>
                </div>

                <div class="grid grid-cols-2 gap-4">
                    <div>
                        <label class="block text-gray-700 mb-1">Expiry Month</label>
                        <input type="number" name="expiryMonth" min="1" max="12" value="${currentCard.expiryMonth}" required class="w-full border rounded px-3 py-2">
                    </div>
                    <div>
                        <label class="block text-gray-700 mb-1">Expiry Year</label>
                        <input type="number" name="expiryYear" min="2024" value="${currentCard.expiryYear}" required class="w-full border rounded px-3 py-2">
                    </div>
                </div>

                <div>
                    <label class="block text-gray-700 mb-1">Card Type</label>
                    <select name="cardType" class="w-full border rounded px-3 py-2" required>
                        <option value="Visa" ${currentCard.cardType == 'Visa' ? 'selected' : ''}>Visa</option>
                        <option value="MasterCard" ${currentCard.cardType == 'MasterCard' ? 'selected' : ''}>MasterCard</option>
                        <option value="Amex" ${currentCard.cardType == 'Amex' ? 'selected' : ''}>Amex</option>
                        <option value="Discover" ${currentCard.cardType == 'Discover' ? 'selected' : ''}>Discover</option>
                    </select>
                </div>

                <div class="flex items-center">
                    <input type="checkbox" name="isDefault" class="mr-2" ${currentCard.isDefault ? 'checked' : ''}>
                    <span class="text-gray-700">Set as default card</span>
                </div>

                <div class="flex justify-end space-x-3">
                    <a href="${pageContext.request.contextPath}/cards" class="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded">Cancel</a>
                    <button type="submit" class="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded">
                        <c:choose>
                            <c:when test="${mode == 'add'}">Save Card</c:when>
                            <c:otherwise>Update Card</c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form>
        </div>
    </c:if>

    <!-- Card Grid -->
    <c:if test="${mode == 'list'}">
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            <c:forEach items="${cards}" var="card">
                <div class="bg-white rounded-2xl shadow-lg p-6 hover:shadow-2xl transition-transform transform hover:scale-105">
                    <h3 class="text-lg font-semibold text-purple-700 mb-2">${card.cardType} Ending in ${card.cardNumberLast4}</h3>
                    <p class="text-gray-600 text-sm mb-1">Cardholder: ${card.cardholderName}</p>
                    <p class="text-gray-500 text-sm mb-1">Expires: ${card.expiryMonth}/${card.expiryYear}</p>

                    <c:if test="${card.isDefault}">
                        <p class="text-sm text-green-500">✔ Default Card</p>
                    </c:if>

                    <div class="mt-4 flex justify-between items-center space-x-2">
                        <a href="${pageContext.request.contextPath}/cards?mode=edit&cardId=${card.cardId}" 
                           class="text-yellow-600 hover:text-yellow-800 text-sm">
                            Edit
                        </a>

                        <c:if test="${!card.isDefault}">
                            <form action="${pageContext.request.contextPath}/cards" method="POST">
                                <input type="hidden" name="action" value="setDefault">
                                <input type="hidden" name="cardId" value="${card.cardId}">
                                <button type="submit" class="text-blue-600 hover:text-blue-800 text-sm">
                                    Set as Default
                                </button>
                            </form>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/cards" method="POST" onsubmit="return confirm('Delete this card?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="cardId" value="${card.cardId}">
                            <button type="submit" class="text-red-600 hover:text-red-800 text-sm">
                                Delete
                            </button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty cards}">
            <div class="text-center text-white mt-10 text-lg">
                You have no saved cards. <br>
                <a href="${pageContext.request.contextPath}/cards?mode=add" class="underline">Add one now!</a>
            </div>
        </c:if>
    </c:if>

</main>

<%@ include file="../User/partials/UserFooter.jsp" %>
