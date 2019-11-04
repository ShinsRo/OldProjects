package com.siotman.wos.jaxws2rest.component;

import com.siotman.wos.jaxws2rest.exception.SidContainerException;
import com.thomsonreuters.wokmws.cxf.auth.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class AuthSidContainer {
    @Autowired
    private AuthenticateServiceWrapper authenticateServiceWrapper;

    private final int SIZE_LIMIT = 5;
    private Random random = new Random();
    private List<SessionId> container = new LinkedList<>();

    public String getSessionId(final String username, final String password) throws SidContainerException {
        String SID = null;

        if (container.size() < SIZE_LIMIT) {
            WOKMWSAuthenticate port = authenticateServiceWrapper.getWOKMWSAuthenticatePort(username, password);
            try {
                SID = port.authenticate();
                container.add(new SessionId(SID));
            } catch (AuthenticationExceptionException e) {
                e.printStackTrace();
            } catch (ESTIWSExceptionException e) {
                e.printStackTrace();
            } catch (InternalServerExceptionException e) {
                e.printStackTrace();
            } catch (InvalidInputExceptionException e) {
                e.printStackTrace();
            } catch (QueryExceptionException e) {
                e.printStackTrace();
            } catch (SessionExceptionException e) {
                e.printStackTrace();
            }
        }

        if (SID == null) {
            try {
                SID = _getRandExistSid(username, password);
            } catch (QueryExceptionException e) {
                e.printStackTrace();
            } catch (SessionExceptionException e) {
                e.printStackTrace();
            } catch (InvalidInputExceptionException e) {
                e.printStackTrace();
            } catch (AuthenticationExceptionException e) {
                e.printStackTrace();
            } catch (InternalServerExceptionException e) {
                e.printStackTrace();
            } catch (ESTIWSExceptionException e) {
                e.printStackTrace();
            }
        }

        if (SID == null || container.size() == 0) {
            throw new SidContainerException();
        }

        return SID;
    }

    public void closeSession(final String SID) {
        WOKMWSAuthenticate port = authenticateServiceWrapper.getWOKMWSAuthenticatePort();

        try {
            port.closeSession();
            container.remove(SID);
        } catch (AuthenticationExceptionException e) {
            e.printStackTrace();
        } catch (ESTIWSExceptionException e) {
            e.printStackTrace();
        } catch (InternalServerExceptionException e) {
            e.printStackTrace();
        } catch (InvalidInputExceptionException e) {
            e.printStackTrace();
        } catch (QueryExceptionException e) {
            e.printStackTrace();
        } catch (SessionExceptionException e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {
        for (SessionId sessionId : container) {
            closeSession(sessionId.getSID());
        }
    }

    private String _getRandExistSid(final String username, final String password)
    throws  QueryExceptionException, SessionExceptionException, InvalidInputExceptionException,
            AuthenticationExceptionException, InternalServerExceptionException, ESTIWSExceptionException {
        int idx = Math.abs(random.nextInt()) % container.size();

        SessionId sessionId = container.get(idx);
        if (Duration.between(sessionId.getCreatedTime(), LocalDateTime.now()).toHours() > 2) {
            WOKMWSAuthenticate port = authenticateServiceWrapper.getWOKMWSAuthenticatePort(username, password);
            String SID = port.authenticate();

            sessionId = new SessionId(SID);
            container.set(idx, sessionId);
        }

        return sessionId.getSID();
    }

    @Getter
    private class SessionId {
        final String SID;
        final LocalDateTime createdTime;

        public SessionId(String SID) {
            this.SID = SID;
            createdTime = LocalDateTime.now();
        }
    }
}
