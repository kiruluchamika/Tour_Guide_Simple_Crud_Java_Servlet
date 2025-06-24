<%@ include file="../User/partials/UserHeader.jsp" %>

<!-- Dashboard Welcome Note -->
<div class="bg-white rounded-3xl shadow-xl p-10 max-w-3xl mx-auto text-center mb-10">
    <h2 class="text-4xl font-bold text-gray-800 mb-4">
        Welcome, ${fullName}!
    </h2>
    <p class="text-lg text-gray-600">
        You're logged in as <span class="text-purple-600 font-semibold">${user.userRole}</span>.
    </p>
</div>

<!-- Stats Cards -->
<div class="grid grid-cols-1 sm:grid-cols-3 gap-6 max-w-5xl mx-auto">
    <div class="bg-gradient-to-r from-purple-500 to-pink-500 text-white p-6 rounded-3xl shadow-lg text-center transform hover:scale-105 transition">
        <h3 class="text-2xl font-bold mb-2">${packageCount}</h3>
        <p class="text-sm">Available Packages</p>
    </div>
    <div class="bg-gradient-to-r from-green-400 to-blue-500 text-white p-6 rounded-3xl shadow-lg text-center transform hover:scale-105 transition">
        <h3 class="text-2xl font-bold mb-2">${bookingCount}</h3>
        <p class="text-sm">My Bookings</p>
    </div>
    <div class="bg-gradient-to-r from-yellow-400 to-red-500 text-white p-6 rounded-3xl shadow-lg text-center transform hover:scale-105 transition">
        <h3 class="text-2xl font-bold mb-2">${reviewCount}</h3>
        <p class="text-sm">My Reviews</p>
    </div>
</div>

<%@ include file="../User/partials/UserFooter.jsp" %>
