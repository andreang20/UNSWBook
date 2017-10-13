<%@ page import="dao.GraphEntity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.GraphEdge" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Clinton
  Date: 10/10/17
  Time: 6:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    ArrayList<GraphEntity> graphEntities = (ArrayList<GraphEntity>) request.getAttribute("graph_entities");
    ArrayList<GraphEdge> graphEdges = (ArrayList<GraphEdge>) request.getAttribute("graph_edges");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Display graph</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.20.1/vis.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.20.1/vis.min.css"></script>

    <style type="text/css">
        #mynetwork {
            width: 600px;
            height: 400px;
            border: 1px solid lightgray;
        }
    </style>

</head>
<body>
    <div id="mynetwork"></div>

    <script type="text/javascript">
        // create an array with nodes
        var nodes = new vis.DataSet([
            <% for (GraphEntity cur: graphEntities) { %>
            {id: <%=cur.getEntityId()%>, label: '<%=cur.getAttribute()%> id: <%=cur.getValue()%>'  <%if (cur.isSelected()) {%>, color: 'red' <%}%>},
            <% } %>
        ]);

        // create an array with edges
        var edges = new vis.DataSet([
            <% for (GraphEdge cur: graphEdges) { %>
            {from: <%=cur.getFrom()%>, to: <%=cur.getTo()%>, arrows: 'to', label: '<%=cur.getRelationship()%>', font: {align: 'horizontal'}},
            <% } %>
        ]);

        // create a network
        var container = document.getElementById('mynetwork');
        var data = {
            nodes: nodes,
            edges: edges
        };
        var options = {
            edges: {
                length: 180
            },
            physics: {
                forceAtlas2Based: {
                    gravitationalConstant: -26,
                    centralGravity: 0.005,
                    springLength: 230,
                    springConstant: 0.18
                },
                maxVelocity: 146,
                solver: 'forceAtlas2Based',
                timestep: 0.35,
                stabilization: {iterations: 150}
            }
        };
        var network = new vis.Network(container, data, options);
    </script>


</body>
</html>
