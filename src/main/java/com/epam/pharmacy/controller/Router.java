package com.epam.pharmacy.controller;

/**
 * The type Router is used for keeping pages URL and the type of sending.
 */
public class Router {
    private String page;
    private Type type = Type.FORWARD;

    /**
     * The enum Type.
     */
    public enum Type {
        /**
         * Forward type.
         */
        FORWARD,
        /**
         * Redirect type.
         */
        REDIRECT
    }

    /**
     * Instantiates a new Router.
     */
    public Router() {
    }

    /**
     * Instantiates a new Router.
     *
     * @param page the page
     */
    public Router(String page) {
        this.page = page;
    }

    /**
     * Instantiates a new Router.
     *
     * @param page the page
     * @param type the type
     */
    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Gets sending type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets sending type redirect.
     */
    public void setTypeRedirect() {
        this.type = Type.REDIRECT;
    }
}
