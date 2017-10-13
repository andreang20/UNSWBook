package controllers;

import dao.*;
import db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@WebServlet("/graph/search")
public class GraphSearch extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String search_type = req.getParameter("type");
            String search_parameter = req.getParameter("parameter");
            GraphEntityDao graphEntityDao = new GraphEntityDao(new DbManager());
            GraphEdgeDao graphEdgeDao = new GraphEdgeDao(new DbManager());

            if (search_type.equals("person")) {
                UserProfileDao userProfileDao = new UserProfileDao(new DbManager());
                ArrayList<UserProfile> res = userProfileDao.searchUsers(search_parameter, null, null);

                ArrayList<GraphEntity> entities = new ArrayList<>();

                // get the corresponding entities
                for (UserProfile cur: res) {
                    GraphEntity toAdd = graphEntityDao.getUniqueId(new GraphEntity("user_profile", cur.getUsername()));
                    if (toAdd != null) {
                        toAdd.setSelected(true);
                        entities.add(toAdd);
                    }
                }

                ArrayList<GraphEdge> edges = new ArrayList<>();
                // get all the edges of each entity
                for (GraphEntity cur: entities) {
                    ArrayList<GraphEdge> resEdges = graphEdgeDao.getNodeEdges(cur.getEntityId());
                    for (GraphEdge curEdge: resEdges) {
                        if (!edges.contains(curEdge)) {
                            edges.add(curEdge);
                        }
                    }

                }

                // get the missing entities from the edges
                for (GraphEdge curEdge: edges) {
                    GraphEntity toAddTo = graphEntityDao.get_entity(curEdge.getTo());
                    if (toAddTo != null) {
                        if (!entities.contains(toAddTo)) {
                            entities.add(toAddTo);
                        }
                    }
                    GraphEntity toAddFrom = graphEntityDao.get_entity(curEdge.getFrom());
                    if (toAddFrom != null) {
                        if (!entities.contains(toAddFrom)) {
                            entities.add(toAddFrom);
                        }
                    }
                }

                req.setAttribute("graph_entities", entities);
                req.setAttribute("graph_edges", edges);

                req.getRequestDispatcher("/display_graph.jsp").forward(req,resp);
                return;

            } else if (search_type.equals("message")) {
                WallPostDao wallPostDao = new WallPostDao(new DbManager());
                // do a search for content of message
                ArrayList<WallPost> posts = wallPostDao.searchPosts(search_parameter);

                ArrayList<GraphEntity> entities = new ArrayList<>();
                // get the corresponding entities
                for (WallPost cur: posts) {
                    GraphEntity toAdd = graphEntityDao.getUniqueId(new GraphEntity("wall_post", Integer.toString(cur.getId())));
                    if (toAdd != null) {
                        toAdd.setSelected(true);
                        entities.add(toAdd);
                    }
                }

                ArrayList<GraphEdge> edges = new ArrayList<>();
                // get all the edges of each entity
                for (GraphEntity cur: entities) {
                    ArrayList<GraphEdge> resEdges = graphEdgeDao.getNodeEdges(cur.getEntityId());
                    for (GraphEdge curEdge: resEdges) {
                        if (!edges.contains(curEdge)) {
                            edges.add(curEdge);
                        }
                    }
                }

                // get the missing entities from the edges
                for (GraphEdge curEdge: edges) {
                    GraphEntity toAddTo = graphEntityDao.get_entity(curEdge.getTo());
                    if (toAddTo != null) {
                        if (!entities.contains(toAddTo)) {
                            entities.add(toAddTo);
                        }
                    }
                    GraphEntity toAddFrom = graphEntityDao.get_entity(curEdge.getFrom());
                    if (toAddFrom != null) {
                        if (!entities.contains(toAddFrom)) {
                            entities.add(toAddFrom);
                        }
                    }
                }

                req.setAttribute("graph_entities", entities);
                req.setAttribute("graph_edges", edges);

                req.getRequestDispatcher("/display_graph.jsp").forward(req,resp);
                return;

            } else if (search_type.equals("friends_of_friends")) {
                UserProfileDao userProfileDao = new UserProfileDao(new DbManager());
                UserProfile user = userProfileDao.getUserProfile(search_parameter);

                ArrayList<GraphEntity> entities = new ArrayList<>();
                ArrayList<GraphEdge> edges = new ArrayList<>();


                if (user != null) {
                    FriendDao friendDao = new FriendDao(new DbManager());
                    // get the friends
                    ArrayList<String> friends = friendDao.get_friends(user.getUsername());

                    // get the friends of the friends
                    ArrayList<String> friendsOfFriends = new ArrayList<>();
                    for(String curFriend: friends) {
                        ArrayList<String> friendsOfCur = friendDao.get_friends(curFriend);
                        for (String a : friendsOfCur) {
                            if (!friendsOfFriends.contains(a)) {
                                friendsOfFriends.add(a);
                            }
                        }
                    }
                    //System.out.println("Friend size "+friends.size());
                    //System.out.println("friend of friend size:"+friendsOfFriends.size());
                    // get the corresponding entities
                    for (String cur: friendsOfFriends) {
                        GraphEntity toAdd = graphEntityDao.getUniqueId(new GraphEntity("user_profile", cur));
                        if (toAdd != null) {
                            toAdd.setSelected(true);
                            entities.add(toAdd);
                        }
                    }

                    // get all the edges of each entity
                    for (GraphEntity cur: entities) {
                        ArrayList<GraphEdge> resEdges = graphEdgeDao.getNodeEdges(cur.getEntityId());
                        for (GraphEdge curEdge: resEdges) {
                            if (!edges.contains(curEdge)) {
                                edges.add(curEdge);
                            }
                        }
                    }

                    // get the missing entities from the edges
                    for (GraphEdge curEdge: edges) {
                        GraphEntity toAddTo = graphEntityDao.get_entity(curEdge.getTo());
                        if (toAddTo != null) {
                            if (!entities.contains(toAddTo)) {
                                entities.add(toAddTo);
                            }
                        }
                        GraphEntity toAddFrom = graphEntityDao.get_entity(curEdge.getFrom());
                        if (toAddFrom != null) {
                            if (!entities.contains(toAddFrom)) {
                                entities.add(toAddFrom);
                            }
                        }
                    }
                }

                req.setAttribute("graph_entities", entities);
                req.setAttribute("graph_edges", edges);

                req.getRequestDispatcher("/display_graph.jsp").forward(req,resp);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/GenericError.jsp");
            return;
        }
    }

}
