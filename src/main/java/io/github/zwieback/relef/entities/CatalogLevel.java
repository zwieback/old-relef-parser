package io.github.zwieback.relef.entities;

public enum CatalogLevel {

    NONE {
        @Override
        public CatalogLevel next() {
            throw new IllegalArgumentException(name() + " level has no next level");
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String getCssQueryForUrl() {
            throw new IllegalArgumentException("No css query for url of catalog level " + name());
        }

        @Override
        public String getCssQueryForNode() {
            throw new IllegalArgumentException("No css query for node of catalog level " + name());
        }

        @Override
        public String getCssQueryForNodeUrl() {
            throw new IllegalArgumentException("No css query for node url of catalog level " + name());
        }
    },

    FIRST {
        @Override
        public CatalogLevel next() {
            return SECOND;
        }

        @Override
        public String getCssQueryForUrl() {
            return "ul.slide-list > li.slide-block > div.title > a";
        }

        @Override
        public String getCssQueryForNode() {
            return "ul.slide-list > li.slide-block";
        }

        @Override
        public String getCssQueryForNodeUrl() {
            return "div.title > a";
        }
    },

    SECOND {
        @Override
        public CatalogLevel next() {
            return THIRD;
        }

        @Override
        public String getCssQueryForUrl() {
            return "ul.inner-slide-list > li > a";
        }

        @Override
        public String getCssQueryForNode() {
            return "ul.inner-slide-list > li.default-state";
        }

        @Override
        public String getCssQueryForNodeUrl() {
            return "a";
        }
    },

    THIRD {
        @Override
        public CatalogLevel next() {
            return FOURTH;
        }

        @Override
        public String getCssQueryForUrl() {
            return "a.slide-list-block__link";
        }

        @Override
        public String getCssQueryForNode() {
            return "div.slide-list-block__item";
        }

        @Override
        public String getCssQueryForNodeUrl() {
            return "a";
        }
    },

    FOURTH {
        @Override
        public CatalogLevel next() {
            throw new IllegalArgumentException(name() + " level has no next level");
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String getCssQueryForUrl() {
            return "ul.slide-list-block__list > li > a";
        }

        @Override
        public String getCssQueryForNode() {
            return "ul.slide-list-block__list > li";
        }

        @Override
        public String getCssQueryForNodeUrl() {
            return "a";
        }
    };


    /**
     * Next level after current.
     * It is required for recursive passage through a tree.
     *
     * @return next level or {@code null} if last
     * @throws IllegalArgumentException for {@code NONE} and {@code FOURTH} levels
     */
    public abstract CatalogLevel next();

    /**
     * Has next level?
     * It is required for recursive passage through a tree.
     *
     * @return {@code true} if last, {@code false} otherwise
     */
    public boolean hasNext() {
        return true;
    }


    /**
     * CSS query to {@code a} with link to url and name of catalog.
     * It is used only for one level of catalogs.
     * <p>
     * Example:
     * <li>
     * url - ul.slide-list > li.slide-block > div.title > a
     * </li>
     *
     * @return css query
     * @throws IllegalArgumentException for {@code NONE} level
     * @see #getCssQueryForNode()
     * @see #getCssQueryForNodeUrl()
     */
    public abstract String getCssQueryForUrl();

    /**
     * CSS query to node of catalog.
     * It is used for recursive passage through a tree.
     * <p>
     * Example:
     * <li>
     * node - ul.slide-list > li.slide-block
     * </li>
     *
     * @return css query
     * @throws IllegalArgumentException for {@code NONE} level
     * @see #getCssQueryForUrl()
     * @see #getCssQueryForNodeUrl()
     */
    public abstract String getCssQueryForNode();

    /**
     * CSS query to url of node of catalog ({@code a} with link to url and name of catalog).
     * It is used for recursive passage through a tree.
     * <p>
     * Example:
     * <li>
     * node     - ul.slide-list > li.slide-block
     * </li>
     * <li>
     * node url - div.title > a
     * </li>
     *
     * @return css query
     * @throws IllegalArgumentException for {@code NONE} level
     * @see #getCssQueryForUrl()
     * @see #getCssQueryForNode()
     */
    public abstract String getCssQueryForNodeUrl();
}
