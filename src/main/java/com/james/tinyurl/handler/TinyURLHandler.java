package com.james.tinyurl.handler;

import com.james.tinyurl.algo.Base62;
import com.james.tinyurl.dao.URLMappingRepository;
import com.james.tinyurl.model.URL;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

/**
 * This class is responsible for the core business logic.
 * TODO: we should do a better job at error handling but I don't have
 * enough time right now!
 * Created by haozhexu on 1/30/17.
 */
public class TinyURLHandler {

    private Logger logger = Logger.getLogger(TinyURLHandler.class);

    @Autowired
    private URLMappingRepository urlMappingRepository;

    /**
     * Generate the short url hash for the given long url in question
     * @param longURL longURL in question
     * @param model model object that we can fill with infomation we would like to present to the view
     * @return {@code true} if the operation succeeds, {@code false} otherwise
     */
    public boolean generateShortURLHash(String longURL, Model model) {
        String shortURLHash;
        boolean isSuccess = false;
        try {
            shortURLHash = getShortHash(longURL);
            model.addAttribute("shortURL", shortURLHash);
            isSuccess = true;
        } catch (Exception e) {
            logger.error("Oh something happened when generating short url hash", e);
            model.addAttribute("error", "Something happened and please try later");
        }
        return isSuccess;
    }

    private String getShortHash(String longURL) {
        CheckDBResponse response = checkLongURLinRepo(longURL);
        if (response.isInRepo) {
            logger.debug(longURL + " is in our database");
            return Base62.fromBase10(response.getLongURL().getId());
        } else {
            return Base62.fromBase10(insertLongURLIntoRepo(longURL));
        }
    }

    /**
     * Checks if the given long url exists in our database or not.
     * @param longURL long URL in question
     * @return a wrapper object {@link CheckDBResponse} wraps around the result
     */
    private CheckDBResponse checkLongURLinRepo(String longURL) {
        List<URL> urls = urlMappingRepository.findAll();
        for (URL url : urls) {
            if (url.getLongURL().equals(longURL)) {
                return new CheckDBResponse().setLongURL(url).setInRepo(true);
            }
        }

        return new CheckDBResponse().setLongURL(null).setInRepo(false);
    }

    /**
     * Insert the the given longURL into our database and return the id.
     * @param longURL longURL we need to insert into our database
     * @return the inserted id
     */
    private int insertLongURLIntoRepo(String longURL) {
        URL url = new URL().setLongURL(longURL);
        urlMappingRepository.saveAndFlush(url);
        return url.getId();
    }

    /**
     * Gets the short hash for the given long url.
     * @param longURL longURL to be converted to short hash
     * @return the short hash for the given long url.
     */
    public String getShortHashFromLongURL(String longURL) {
        try {
            return getShortHash(longURL);
        } catch (Exception e) {
            logger.error("Oh no! Something happened when gettting short Hash from Long URL", e);
        }
        return null;
    }

    /**
     * For the given short Hash, gets corresponding long url.
     * @param shortHash short hash in question
     * @return corresponding long url for the given short hash
     */
    public String retrieveLongURLByShortHash(String shortHash) {
        int idToQuery = Base62.toBase10(shortHash);
        logger.debug(shortHash + " id is " + idToQuery);
        URL url = urlMappingRepository.findOne(idToQuery);
        return url == null ? null : url.getLongURL();
    }

    /**
     * Simple data structure to make our life a little easy.
     */
    @Data
    @Accessors(chain = true)
    private static class CheckDBResponse {
        @Getter
        @Setter
        private URL longURL;

        @Getter
        @Setter
        private boolean isInRepo;
    }
}
