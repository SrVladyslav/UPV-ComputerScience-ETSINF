import json
from loguru import logger
from spade.behaviour import OneShotBehaviour
from spade.template import Template
from spade.message import Message
from pygomas.bditroop import BDITroop
from pygomas.bdifieldop import BDIFieldOp
from agentspeak import Actions
from agentspeak import grounded
from agentspeak.stdlib import actions as asp_action
from pygomas.ontology import HEALTH
import random as rnd
from pygomas.ontology import DESTINATION
import math
import random
from pygomas.agent import LONG_RECEIVE_WAIT

class BDITropa(BDIFieldOp, BDITroop):

        def add_custom_actions(self, actions):
            super().add_custom_actions(actions)

        
            @actions.add_function(".distance", (tuple,tuple, ))
            def _distance(p1, p2):
                return ((p1[0]-p2[0])**2+(p1[2]-p2[2])**2)**0.5

            @actions.add_function(".flagTaken", ())
            def _flagTaken():
                '''
                    @rerturn 1 if the flag is taken, else  0
                '''
                return 1 if self.is_objective_carried else 0
                
            @actions.add_function(".soldiers",())
            def _soldiers():
                '''
                    Returns the number of alive soldiers
                '''
                return self.soldiers_count
            
            @actions.add_function(".distMedia", (tuple,tuple, ))
            def _distMedia(p1, p2):
                return ((p1[0] + p2[0])/2, 0, (p1[2]+ p2[2])/2)
            
            @actions.add_function(".delF",(tuple,))
            def _delF(t):
                '''
                    Returns the list without 0index element
                '''
                return t[1:]

            @actions.add_function(".canGO", (tuple, ))
            def _canGO(position):
                '''
                    Looks if the agent can got to the position or not, 
                    very grateful when you need to know if the wall are there
                '''
                
                X, Y, Z = position
                return 1 if self.map.can_walk(X, Z) else 0
            
            @actions.add_function(".tryGO", (tuple, tuple, ))
            def _tryGO(position, posEnemy):
                '''
                    Looks if we can move to the position, if yes, we move there, 
                    if not, we just move to the enemy position
                '''
                X, Y, Z = position
                if self.map.can_walk(X,Z):
                    print("We can go !")
                    return position
                else:
                    return posEnemy


            @actions.add_function(".next", (tuple,tuple, ))
            def _next(pos, flag):
                '''
                    Gives the next possible point to go randomly
                '''
                (px, py, pz) = pos
                (fx, fy, fz) = flag
                points  = [(px, pz), (fx, fz)]

                def dist(p1, p2):
                    return ((p1[0]-p2[0])**2+(p1[1]-p2[1])**2)**0.5
                
                for i in range(len(points)):
                    p1 = points[i][0] + random.randint(10,10)
                    p2 = points[i][1] + random.randint(10,10)
                    while(not self.map.can_walk(p1, p2) and dist((p1, p2),(fx, fz)) > 80):
                        p1 = points[i][0] + random.randint(10,10)
                        p2 = points[i][1] + random.randint(10,10)
                
                return (p1, 0, p2)
        
            @actions.add_function(".focusedAT", ())
            def _focusedAT():
                '''
                    Returns the agent ID of the agent or None
                ''' 
                return self.aimed_agent
            
            @actions.add_function(".focuseAT", (tuple,tuple ))
            def _focuseAT(pos, flag):
                '''
                    Returns the agent ID of the nearest to flag
                ''' 
                def dist(p1, p2):
                    return ((p1[0]-p2[0])**2+(p1[2]-p2[2])**2)**0.5
                mi = flag
                d = 200
                for i in range(len(pos)):
                    if dist(pos[i], flag) < d:
                        mi = pos[i]
                return mi

            @actions.add_function(".defencePOS", (tuple, ))
            def _defencePOS(flagPOS):
                '''
                    Given the agents list and the flag position, 
                    we'll calculate 4 positions of defence in circle,
                    with the minimum distance between the agents so they could 
                    come fast to help, the final list will be the 4 positions for agents.

                    Also we know that the view range of agents are 50v.p, so the remaining
                    distance will be 24 v.p between agents, if not, this distance will
                    decrease.
                '''
                fX, fY, fZ = flagPOS

                positions = []
                cc = 25#17 # CC == CO
                print("[ L ]: The flag is at [",fX,",",fZ,"]" )
                   
                x = 0; z = 0
                i = 0
                # left up
                x = fX - cc
                z = fZ + cc
                while not self.map.can_walk(fX - cc, fZ + cc):
                    x = fX - cc + i
                    z = fZ + cc - i
                    i += 1
                pos1 = (x, 0, z)    

                i = 0
                # right up
                x = fX + cc
                z = fZ + cc
                while not self.map.can_walk(fX - cc, fZ + cc):
                    x = fX + cc - i
                    z = fZ + cc - i
                    i += 1
                pos2 = (x, 0, z) 

                i = 0
                # left down
                x = fX - cc
                z = fZ - cc
                while not self.map.can_walk(fX - cc, fZ + cc):
                    x = fX - cc + i
                    z = fZ - cc + i
                    i += 1
                pos4 = (x, 0, z) 

                i = 0
                # right down
                x = fX + cc
                z = fZ - cc
                while not self.map.can_walk(fX - cc, fZ + cc):
                    x = fX + cc - i
                    z = fZ - cc + i
                    i += 1
                pos3 = (x, 0, z) 

                print("Puntos: ", pos1, pos2, pos3, pos4)
                return (pos1, pos2, pos3, pos4)




