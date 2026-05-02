package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Professor;
import service.DataStore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/professors")
public class ProfessorServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final DataStore store = DataStore.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
            String deptId = body.get("dept_short_id").getAsString();
            Professor prof = gson.fromJson(body.get("professor"), Professor.class);
            boolean added = store.addProfessor(deptId, prof);
            if (!added) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(gson.toJson(new ErrorResponse("Department not found")));
                return;
            }
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print(gson.toJson(prof));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new ErrorResponse(e.getMessage())));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
            String deptId = body.get("dept_short_id").getAsString();
            Professor prof = gson.fromJson(body.get("professor"), Professor.class);
            boolean updated = store.updateProfessor(deptId, prof);
            if (!updated) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(gson.toJson(new ErrorResponse("Professor or department not found")));
                return;
            }
            out.print(gson.toJson(prof));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new ErrorResponse(e.getMessage())));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            String deptId = req.getParameter("dept");
            String profId = req.getParameter("id");
            boolean removed = store.removeProfessor(deptId, profId);
            if (!removed) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(gson.toJson(new ErrorResponse("Professor or department not found")));
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new ErrorResponse(e.getMessage())));
        }
    }

    private static class ErrorResponse {
        String error;
        ErrorResponse(String error) { this.error = error; }
    }
}
