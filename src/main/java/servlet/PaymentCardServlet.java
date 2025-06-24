package servlet;

import model.PaymentCard;
import service.PaymentCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cards")
public class PaymentCardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentCardService cardService;

    @Override
    public void init() throws ServletException {
        cardService = new PaymentCardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/User/UserLogin.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String mode = request.getParameter("mode");
        if (mode == null) mode = "list";
        request.setAttribute("mode", mode);

        if ("add".equals(mode)) {
            request.getRequestDispatcher("/User/ManageCards.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(mode)) {
            int cardId = Integer.parseInt(request.getParameter("cardId"));
            PaymentCard currentCard = cardService.getCardById(cardId);
            request.setAttribute("currentCard", currentCard);
            request.getRequestDispatcher("/User/ManageCards.jsp").forward(request, response);
            return;
        }

        List<PaymentCard> cards = cardService.getCardsByUserId(userId);
        request.setAttribute("cards", cards);

        request.getRequestDispatcher("/User/ManageCards.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/User/UserLogin.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");
        String message;
        boolean isError = false;

        try {
            switch (action) {
                case "add":
                    PaymentCard newCard = new PaymentCard();
                    newCard.setUserId(userId);
                    newCard.setCardholderName(request.getParameter("cardholderName"));
                    String fullCardNumber = request.getParameter("cardNumber");
                    newCard.setCardNumberLast4(fullCardNumber.substring(fullCardNumber.length() - 4));
                    newCard.setCardType(request.getParameter("cardType"));
                    newCard.setExpiryMonth(Integer.parseInt(request.getParameter("expiryMonth")));
                    newCard.setExpiryYear(Integer.parseInt(request.getParameter("expiryYear")));
                    newCard.setIsDefault("on".equals(request.getParameter("isDefault")));

                    if (newCard.getIsDefault()) {
                        cardService.setDefaultCard(userId, 0);
                    }

                    if (cardService.addPaymentCard(newCard)) {
                        message = "Card added successfully.";
                    } else {
                        message = "Failed to add card.";
                        isError = true;
                    }
                    break;

                case "update":
                    PaymentCard cardToUpdate = new PaymentCard();
                    cardToUpdate.setCardId(Integer.parseInt(request.getParameter("cardId")));
                    cardToUpdate.setCardholderName(request.getParameter("cardholderName"));
                    cardToUpdate.setCardType(request.getParameter("cardType"));
                    cardToUpdate.setExpiryMonth(Integer.parseInt(request.getParameter("expiryMonth")));
                    cardToUpdate.setExpiryYear(Integer.parseInt(request.getParameter("expiryYear")));
                    cardToUpdate.setIsDefault("on".equals(request.getParameter("isDefault")));

                    if (cardToUpdate.getIsDefault()) {
                        cardService.setDefaultCard(userId, 0);
                    }

                    if (cardService.updatePaymentCard(cardToUpdate)) {
                        message = "Card updated successfully.";
                    } else {
                        message = "Failed to update card.";
                        isError = true;
                    }
                    break;

                case "delete":
                    int cardIdToDelete = Integer.parseInt(request.getParameter("cardId"));
                    if (cardService.deleteCard(cardIdToDelete)) {
                        message = "Card deleted successfully.";
                    } else {
                        message = "Failed to delete card.";
                        isError = true;
                    }
                    break;

                case "setDefault":
                    int cardId = Integer.parseInt(request.getParameter("cardId"));
                    if (cardService.setDefaultCard(userId, cardId)) {
                        message = "Default card updated.";
                    } else {
                        message = "Failed to set default card.";
                        isError = true;
                    }
                    break;

                default:
                    message = "Unknown action.";
                    isError = true;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
            isError = true;
        }

        if (isError) {
            request.getSession().setAttribute("error", message);
        } else {
            request.getSession().setAttribute("success", message);
        }

        response.sendRedirect(request.getContextPath() + "/cards");
    }
}
