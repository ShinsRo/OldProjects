package com.siotman.wos.jaxws2rest.component;

import com.siotman.wos.jaxws2rest.exception.SidContainerException;
import com.thomsonreuters.wokmws.cxf.auth.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        if (System.nanoTime() - sessionId.getCreatedTime() > TimeUnit.HOURS.toNanos(2)) {
            WOKMWSAuthenticate port = authenticateServiceWrapper.getWOKMWSAuthenticatePort(username, password);
            String SID = port.authenticate();

            sessionId = new SessionId(SID);
            container.set(idx, sessionId);
        }

        return sessionId.getSID();
    }

    @Data
    private class SessionId {
        String SID;
        Long createdTime;

        public SessionId(String SID) {
            this.SID = SID;
            createdTime = System.nanoTime();
        }
    }
}
