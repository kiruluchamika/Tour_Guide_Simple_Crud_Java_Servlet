<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../User/partials/UserHeader.jsp" %>

<main class="p-6">

    <h2 class="text-4xl font-bold text-white mb-8 text-center">Explore Our Packages</h2>

    <c:if test="${empty packages}">
        <div class="text-center text-white text-lg">
            No packages available at the moment. Please check back later!
        </div>
    </c:if>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
        <c:forEach items="${packages}" var="pkg">
            <div class="relative bg-white rounded-3xl shadow-lg overflow-hidden hover:shadow-2xl transition transform hover:scale-105">
                
                <!-- Package ID Badge -->
                <div class="absolute top-3 right-3 bg-purple-600 text-white text-xs font-bold px-3 py-1 rounded-full shadow">
                    ID: ${pkg.packageId}
                </div>

                <!-- Dynamic Image Based on Package Type -->
                <c:choose>
                    <c:when test="${pkg.packageType == 'Adventure'}">
                        <img src="${pageContext.request.contextPath}/User/assets/images/adventure.jpg" class="h-48 w-full object-cover" alt="Adventure Package">
                    </c:when>
                    <c:when test="${pkg.packageType == 'Leisure'}">
                        <img src="${pageContext.request.contextPath}/User/assets/images/leisure.jpg" class="h-48 w-full object-cover" alt="Leisure Package">
                    </c:when>
                    <c:when test="${pkg.packageType == 'Cultural'}">
                        <img src="${pageContext.request.contextPath}/User/assets/images/cultural.jpg" class="h-48 w-full object-cover" alt="Cultural Package">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/User/assets/images/default.jpg" class="h-48 w-full object-cover" alt="Package">
                    </c:otherwise>
                </c:choose>

                <div class="p-6">
                    <h3 class="text-2xl font-semibold text-purple-700 mb-2">${pkg.packageName}</h3>
                    <p class="text-gray-600 mb-4 line-clamp-3">${pkg.description}</p>

                    <div class="flex justify-between items-center text-sm text-gray-500 mb-4">
                        <span><i class="fas fa-map-marker-alt mr-1"></i>${pkg.location}</span>
                        <span><i class="fas fa-clock mr-1"></i>${pkg.duration} Days</span>
                    </div>

                    <div class="flex justify-between items-center">
                        <span class="text-xl font-bold text-green-500">$${pkg.price}</span>
                        <span class="text-xs text-gray-400">
                            ${pkg.availableFrom} - ${pkg.availableUntil}
                        </span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

</main>

<%@ include file="../User/partials/UserFooter.jsp" %>
