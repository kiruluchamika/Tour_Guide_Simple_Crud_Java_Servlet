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

    <!-- Create/Edit Review Form -->
    <c:if test="${mode == 'create' || mode == 'edit'}">
        <div class="bg-white rounded-3xl shadow p-6 mb-6 transition-transform transform hover:scale-105">
            <h2 class="text-2xl font-semibold mb-4 text-purple-700">
                <c:choose>
                    <c:when test="${mode == 'create'}">Write a New Review</c:when>
                    <c:otherwise>Edit Review</c:otherwise>
                </c:choose>
            </h2>

            <form action="${pageContext.request.contextPath}/reviews" method="POST">
                <input type="hidden" name="action" value="${mode == 'create' ? 'create' : 'update'}">
                <c:if test="${mode == 'edit'}">
                    <input type="hidden" name="id" value="${currentReview.reviewId}">
                    <input type="hidden" name="isVerified" value="${currentReview.verified}">
                </c:if>

                <div class="mb-4">
                    <label class="block text-gray-700 mb-2">Package ID</label>
                    <input type="number" name="packageId" value="${currentReview.packageId}" 
                           class="w-full border rounded px-3 py-2" required ${mode == 'edit' ? 'readonly' : ''}>
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 mb-2">Title</label>
                    <input type="text" name="title" value="${currentReview.title}" 
                           class="w-full border rounded px-3 py-2" required>
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 mb-2">Rating (1-5)</label>
                    <input type="number" name="rating" min="1" max="5" value="${currentReview.rating}" 
                           class="w-full border rounded px-3 py-2" required>
                </div>

                <div class="mb-4">
                    <label class="block text-gray-700 mb-2">Review</label>
                    <textarea name="reviewText" class="w-full border rounded px-3 py-2" required>${currentReview.reviewText}</textarea>
                </div>

                <div class="flex justify-end space-x-3">
                    <a href="${pageContext.request.contextPath}/reviews" 
                       class="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded">
                        Cancel
                    </a>
                    <button type="submit" class="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded">
                        <c:choose>
                            <c:when test="${mode == 'create'}">Submit Review</c:when>
                            <c:otherwise>Update Review</c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form>
        </div>
    </c:if>

    <!-- View Review Details -->
    <c:if test="${mode == 'view'}">
        <div class="bg-white rounded-3xl shadow p-6 mb-6 transition-opacity duration-500">
            <h2 class="text-2xl font-semibold mb-4 text-purple-700">Review Details</h2>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                <div>
                    <label class="block text-gray-500">Package ID</label>
                    <p class="font-medium">${currentReview.packageId}</p>
                </div>
                <div>
                    <label class="block text-gray-500">Title</label>
                    <p class="font-medium">${currentReview.title}</p>
                </div>
                <div>
                    <label class="block text-gray-500">Rating</label>
                    <p class="font-medium text-yellow-600">${currentReview.rating} / 5</p>
                </div>
                <div>
                    <label class="block text-gray-500">Verified</label>
                    <p class="font-medium">
                        <c:if test="${currentReview.verified}">✔ Yes</c:if>
                        <c:if test="${!currentReview.verified}">✖ No</c:if>
                    </p>
                </div>
                <div class="md:col-span-2">
                    <label class="block text-gray-500">Review</label>
                    <p class="font-medium">${currentReview.reviewText}</p>
                </div>
            </div>

            <div class="flex justify-end">
                <a href="${pageContext.request.contextPath}/reviews" 
                   class="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded">
                    Back to List
                </a>
            </div>
        </div>
    </c:if>

    <!-- List of Reviews -->
    <c:if test="${mode == 'list'}">
        <div class="bg-white rounded-3xl shadow overflow-hidden">
            <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
                <h2 class="text-2xl font-semibold text-gray-800">My Reviews</h2>
                <a href="${pageContext.request.contextPath}/reviews?mode=create" 
                   class="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-md text-sm font-medium">
                    <i class="fas fa-plus mr-2"></i>Write Review
                </a>
            </div>

            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Package ID</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Rating</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <c:forEach items="${reviews}" var="review">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap">${review.packageId}</td>
                                <td class="px-6 py-4 whitespace-nowrap">${review.title}</td>
                                <td class="px-6 py-4 whitespace-nowrap">${review.rating} / 5</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                    <a href="${pageContext.request.contextPath}/reviews?mode=view&id=${review.reviewId}" 
                                       class="text-blue-600 hover:text-blue-900 mr-3">
                                        <i class="fas fa-eye"></i> View
                                    </a>
                                    <a href="${pageContext.request.contextPath}/reviews?mode=edit&id=${review.reviewId}" 
                                       class="text-yellow-600 hover:text-yellow-900 mr-3">
                                        <i class="fas fa-edit"></i> Edit
                                    </a>
                                    <form action="${pageContext.request.contextPath}/reviews" method="POST" class="inline">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${review.reviewId}">
                                        <button type="submit" class="text-red-600 hover:text-red-900"
                                            onclick="return confirm('Are you sure you want to delete this review?')">
                                            <i class="fas fa-trash"></i> Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

</main>

<%@ include file="../User/partials/UserFooter.jsp" %>
