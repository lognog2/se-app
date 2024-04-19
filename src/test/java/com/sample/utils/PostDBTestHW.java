package com.sample.utils;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class PostDBTestHW {
    private PostDB db;

    @BeforeEach
    public void setupEach() throws SQLException,ClassNotFoundException {
        db = new PostDB("");

        // Random DBName so each test runs independently
        LoginInfo li = new LoginInfo(
                "", "" + Math.random(), "", "", null, null, null);
        // Use in-memory database
        db.Login(li, PostDB.H2_DRIVER);
        assertTrue(db.GetLoggedIn());
    }

    @AfterEach
    public void teardownEach() {
        db.Logout();
        assertFalse(db.GetLoggedIn());
        db = null;
    }
    // WRITE TESTS UNDER HERE
   
    //1. A user adds a post and then deletes it; the post should no longer be in the database.
    @Test
    public void testPostAddDelete()
    {
        String testString = "test";
        assertTrue(db.addPost(testString));
        assertTrue(db.deletePost(testString));
        assertFalse(db.getAllPosts().contains(testString));
    }
    //2. A user tries to delete a post that doesn't exist; the database should be unchanged.
    @Test
    public void testDeleteNonexistent()
    {
        String testString = "test";
        assertEquals(db.getAllPosts(), "timeStamp\ttext\n");
        assertFalse(db.deletePost(testString));
        assertEquals(db.getAllPosts(), "timeStamp\ttext\n");
    }

    //3. A user adds a post, e.g. "abcd", then tries to delete the post by specifying it in all upper case, e.g. "ABCD"; 
    //this should fail and the database should be unchanged.
    @Test
    public void testWrongCase()
    {
        String testString = "abcd";
        assertTrue(db.addPost(testString));
        String oldDB = db.getAllPosts();
        assertFalse(db.deletePost(testString.toUpperCase()));
        String newDB = db.getAllPosts();
        assertEquals(oldDB, newDB);
    }

    //4. A user tries to add a post with no text (i.e. "" in Java); this should fail and the database should be unchanged.
    @Test
    public void testAddEmptyText()
    {
        String emptyString = "";
        assertEquals(db.getAllPosts(), "timeStamp\ttext\n");
        assertFalse(db.addPost(emptyString));
        assertEquals(db.getAllPosts(), "timeStamp\ttext\n");
    }

    //5. A user tries to delete a post with no text; this should fail and the database should be unchanged.
    @Test
    public void testDeleteEmptyText()
    {
        String emptyString = "";
        assertEquals(db.getAllPosts(), "timeStamp\ttext\n");
        assertFalse(db.deletePost(emptyString));
        assertEquals(db.getAllPosts(), "timeStamp\ttext\n");
    }

    // WRITE TESTS ABOVE HERE
}
