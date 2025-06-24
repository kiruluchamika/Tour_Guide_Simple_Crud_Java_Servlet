<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Session validation using implicit session object
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/User/UserLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard | Tour Guide</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .fade-in {
            animation: fadeIn 0.8s ease-in-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body class="bg-gradient-to-br from-blue-500 via-purple-500 to-pink-500 min-h-screen flex">

<!-- Sidebar -->
<aside class="w-64 bg-white shadow-lg min-h-screen p-6 flex flex-col">
    <div class="text-3xl font-extrabold text-purple-600 mb-10 text-center">
        TourGuide
    </div>
    <nav class="flex-1">
        <ul class="space-y-4">
            <li>
                <a href="${pageContext.request.contextPath}/user/dashboard" 
                   class="flex items-center text-gray-700 hover:text-purple-600 transition font-semibold">
                    <i class="fas fa-home mr-3"></i> Dashboard
                </a>
            </li>
			<li>
			    <a href="${pageContext.request.contextPath}/packages"
			       class="flex items-center text-gray-700 hover:text-purple-600 transition font-semibold">
			        <i class="fas fa-box-open mr-3"></i> View Packages
			    </a>
			</li>
            <li>
                <a href="${pageContext.request.contextPath}/bookings" 
                   class="flex items-center text-gray-700 hover:text-purple-600 transition font-semibold">
                    <i class="fas fa-suitcase-rolling mr-3"></i> My Bookings
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/reviews" 
                   class="flex items-center text-gray-700 hover:text-purple-600 transition font-semibold">
                    <i class="fas fa-star mr-3"></i> Reviews
                </a>
            </li>
			<li>
			    <a href="${pageContext.request.contextPath}/cards" 
			       class="flex items-center text-gray-700 hover:text-purple-600 transition font-semibold">
			        <i class="fas fa-credit-card mr-3"></i> Payments
			    </a>
			</li>           
        </ul>
    </nav>
    <div class="mt-10">
        <a href="${pageContext.request.contextPath}/user/logout" 
           class="flex items-center bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-full transition justify-center">
            <i class="fas fa-sign-out-alt mr-2"></i> Logout
        </a>
    </div>
</aside>

<!-- Main Content Wrapper -->
<div class="flex-1 p-8 fade-in">
