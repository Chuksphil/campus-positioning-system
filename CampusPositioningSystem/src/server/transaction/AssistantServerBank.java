package server.transaction;

import java.util.LinkedList;
import java.util.Queue;

import message.ServerType;

public class AssistantServerBank {

	private Queue<LocationAssistantServer> m_locationServers = new LinkedList<LocationAssistantServer>();
	private Queue<NavigationAssistantServer> m_navigationServers = new LinkedList<NavigationAssistantServer>();
	private Queue<PositionAssistantServer> m_positionServers = new LinkedList<PositionAssistantServer>();
	
		
	
	public synchronized void AddAssistantServer(AssistantServer server)
	{
		if (server.GetType() == ServerType.Location)
		{
			m_locationServers.add((LocationAssistantServer)server);
		}
		else if(server.GetType() == ServerType.Navigation)
		{			
			m_navigationServers.add((NavigationAssistantServer)server);
		}
		else if(server.GetType() == ServerType.Position)
		{
			m_positionServers.add((PositionAssistantServer)server);	
		}		
	}
	
	public synchronized void ReportBadServer(AssistantServer server)
	{
		if (server.GetType() == ServerType.Location)
		{
			m_locationServers.remove((LocationAssistantServer)server);
		}
		else if(server.GetType() == ServerType.Navigation)
		{			
			m_navigationServers.remove((NavigationAssistantServer)server);
		}
		else if(server.GetType() == ServerType.Position)
		{
			m_positionServers.remove((PositionAssistantServer)server);	
		}
	}
	
	
	public synchronized LocationAssistantServer GetNextLocationAssistantServer()
	{
		LocationAssistantServer server = m_locationServers.remove();
		m_locationServers.add(server);
		return server;
	}

	public synchronized NavigationAssistantServer GetNextNavigationAssistantServer()
	{
		NavigationAssistantServer server = m_navigationServers.remove();
		m_navigationServers.add(server);
		return server;
	}

	public synchronized PositionAssistantServer GetNextPositionAssistantServer()
	{
		PositionAssistantServer server = m_positionServers.remove();
		m_positionServers.add(server);
		return server;
	}
	
	
	
	
	
}
